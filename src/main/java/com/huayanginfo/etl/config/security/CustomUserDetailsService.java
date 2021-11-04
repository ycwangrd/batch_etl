package com.huayanginfo.etl.config.security;

import cn.hutool.core.collection.CollUtil;
import com.huayanginfo.etl.model.entity.JdpSysMenu;
import com.huayanginfo.etl.model.entity.JdpSysRole;
import com.huayanginfo.etl.model.entity.JdpSysUser;
import com.huayanginfo.etl.service.JdpSysUserRoleService;
import com.huayanginfo.etl.service.JdpSysUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月13日 星期五 13:35:20
 */
@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private JdpSysUserService userService;
    @Autowired
    private JdpSysUserRoleService userRoleService;

    /**
     * 实现UserDetailsService的方法
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        JdpSysUser sysUser = userService.findOne(username);
        if (sysUser != null) {
            // 返回包括权限角色的User给security
            List<JdpSysRole> userRoles = userRoleService.findRolesByUserId(sysUser.getUserId());
            // 角色授权：授权代码需要加ROLE_前缀，controller上使用时不要加前缀
            List<GrantedAuthority> authorityList = new ArrayList<>(30);
            if (CollUtil.isNotEmpty(userRoles)) {
                userRoles.forEach(sysRole -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleCode())));
            }
            List<JdpSysMenu> sysMenus = userRoleService.findMenusByUserId(sysUser.getUserId());
            if (CollUtil.isNotEmpty(sysMenus)) {
                sysMenus.forEach(sysMenu -> authorityList.add(new SimpleGrantedAuthority(sysMenu.getPagePath())));
            }
            User authUser = new User(sysUser.getUserName(), sysUser.getUserPasswd(), true, true, true, true, authorityList);
            return authUser;
        }
        return null;
    }
}
