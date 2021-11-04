package com.huayanginfo.etl.config;

import com.huayanginfo.etl.listener.TaskExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月20日 星期五 10:11:21
 */
public abstract class DataBatchBase {
    /**
     * 用于构建Step
     */
    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    /**
     * ç
     */
    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    protected JobLauncher jobLauncher;

    /**
     * 注入实例化Factory 访问数据
     */
    @PersistenceUnit
    protected EntityManagerFactory defaultEmf;

    /**
     * 任务执行的监控
     */
    @Autowired
    protected TaskExecutionListener executionListener;
}
