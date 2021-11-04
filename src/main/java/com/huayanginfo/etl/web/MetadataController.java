package com.huayanginfo.etl.web;

import com.huayanginfo.etl.utils.JdbcDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月04日 星期三 16:49:19
 */
@Log4j2
@RestController
@RequestMapping(value = "/metadata")
public class MetadataController {
    @Autowired
    private JdbcDataService jdbcDataService;

    @GetMapping("/createMetadata")
    // 一下两个注解用任意一个都可以
    @Secured(value = "ROLE_settings")
    // @RolesAllowed(value = "settings") // TODO 在使用JSR-250注解时，ROLE_ 前缀可省略，而在使用@Secured是不能省略前缀的！！
    public String createMetadata() {
        StringBuffer initDbSql = new StringBuffer();
        String[] tableNames = new String[]{
                "cms_article_channel",
                "cms_user",
                "jdp_sys_calendar",
                "jdp_sys_menu",
                "jdp_sys_org",
                "jdp_sys_role",
                "jdp_sys_role_menu",
                "jdp_sys_user",
                "jdp_sys_user_role"
        };
        for (String tableName : tableNames) {
            String tableSql = jdbcDataService.changeMysqlTable2ClickHouse(tableName);
            initDbSql.append(tableSql + ";\r\n");
        }
        return initDbSql.toString();
    }
}
