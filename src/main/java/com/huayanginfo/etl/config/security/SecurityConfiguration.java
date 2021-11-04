package com.huayanginfo.etl.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月12日 星期四 18:53:08
 * Spring Security 的配置
 */
@Log4j2
@Configuration
// 开启 Security 服务
@EnableWebSecurity
// 开启全局 Securtiy 注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
@DependsOn({"jdpSysUserService"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 定义spring security提供的默认密码加密方案
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return 封装身份认证提供者
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 自定义的用户和角色数据提供者
        authProvider.setUserDetailsService(userDetailsService);
        // 设置密码加密对象
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * 主要配置身份认证来源，也就是用户及其角色
     *
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 设置身份认证提供者
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 主要配置路径，也就是资源的访问权限（是否需要认证，需要什么角色等）
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/vercode");
    }

    /**
     * 配置登录,注销和权限路径
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 可以设置前置filter,比如验证码验证等等
        // http.addFilterBefore();
        // 关闭csrf防护 >只有关闭了,才能接收来自表单的请求
        http.csrf().disable();
        // url拦截认证  >所有请求都必须被认证 必须登录后才可以访问
        http.authorizeRequests() // 开启登录配置
                // 允许配置匿名用户的表示方法。默认情况下，匿名用户将使用org.springframework.security.authentication.AnonymousAuthenticationToken表示，并包含角色 “ROLE_ANONYMOUS”
                .antMatchers("/api/*", "/swager-ui/*", "/login/*").permitAll()
                // TODO 设置其他页面权限
                // 表示剩余的其他接口，登录之后就能访问
                .anyRequest().authenticated();
        // 表单认证
        http.formLogin()
                // 定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
                .loginPage("/login/login")
                //定义登录时，用户名的 key，默认为 username
                .usernameParameter("username")
                //定义登录时，用户密码的 key，默认为 password
                .passwordParameter("password")
                // 当发现login时认为是登录需要执行我们自定义的登录逻辑 >里面的url是登录页面表单的提交地址(登录处理接口)
                .loginProcessingUrl("/login/doLogin")
                //登录成功后请求地址 请求方法必须是post的
                // .successForwardUrl("/home")
                // 登录成功的处理器
                .successHandler((request, response, authentication) -> {
                    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                    PrintWriter out = response.getWriter();
                    out.write("success");
                    out.flush();
                })
                // 登录失败的处理器
                .failureHandler((request, response, exception) -> {
                    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                    PrintWriter out = response.getWriter();
                    out.write("fail");
                    out.flush();
                })
                // 和表单登录相关的接口统统都直接通过
                .permitAll().and().userDetailsService(userDetailsService);
        // 允许配置“记住我”的验证
        http.rememberMe();
        // 设置退出
        http.logout()
                // 设置退出地址,默认值是/
                .logoutUrl("/login/logout")
                // 设置注销成功后的重定向地址
                // .logoutSuccessUrl("/")
                // 添加一些注销的逻辑,可以与LogoutHandler合并
                .addLogoutHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    String userName = authentication.getName();
                    log.info("用户[{}]执行注销..", userName);
                })
                // 设置注销登录成功后的处理器
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                    PrintWriter out = response.getWriter();
                    out.write("success");
                    out.flush();
                })
                .permitAll()
                // 使用用户的httpSession失效
                .invalidateHttpSession(true)
                // 注销成功,删除指定的cookie
                .deleteCookies("remember-me");
    }
}
