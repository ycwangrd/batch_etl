package com.huayanginfo.etl.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月20日 星期五 10:26:55
 */
@Log4j2
@Component
public class TaskExecutionListener implements JobExecutionListener {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime.set(System.currentTimeMillis());
        log.info("job before " + jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("任务Job状态 : {}", jobExecution.getStatus());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("任务Job已完成.");
            threadPoolTaskExecutor.destroy();
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("任务Job执行失败");
        }
        log.info("任务Job耗时 : {}ms", (System.currentTimeMillis() - startTime.get()));
        startTime.remove();
    }
}