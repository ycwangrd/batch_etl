package com.huayanginfo.etl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月04日 星期三 16:06:17
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * Swagger的Docket配置
     *
     * @return Swagger的Docket对象
     */
    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("华洋峻峰搜索服务接口服务文档")
                .description("服务接口文档,基于Swagger3版本。")
                .contact(new Contact("北京华洋峻峰信息工程股份公司", "https://www.huayanginfo.com", "wang.ruidong@huayanginfo.com"))
                .version("1.1")
                .build();
        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .apiInfo(apiInfo)
                .select()
                // 扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.huayanginfo.etl.web"))
                // 选择API路径
                .paths(PathSelectors.regex("^.*(?<!error)$"))
                .build();
    }
}

