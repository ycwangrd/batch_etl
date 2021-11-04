package com.huayanginfo.etl;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月04日 星期三 15:45:34
 * <p style="font-size:12px;font-weight:bold;">
 * 基于spring-batch,quartz定时将MySQL数据库中的数据进行ETL到其他库中
 * </p>
 */
@Log4j2
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
// 开启批处理的支持
@EnableBatchProcessing
@EnableAsync
public class BatchEtlApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BatchEtlApplication.class, args);
        log.info("应用启动成功...");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BatchEtlApplication.class);
    }
}