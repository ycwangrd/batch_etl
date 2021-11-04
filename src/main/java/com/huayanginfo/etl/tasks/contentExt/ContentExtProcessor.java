package com.huayanginfo.etl.tasks.contentExt;

import cn.hutool.core.bean.BeanUtil;
import com.huayanginfo.etl.config.DataBatchBase;
import com.huayanginfo.etl.model.master.MasterContentExt;
import com.huayanginfo.etl.model.slave.SlaveContentExt;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月24日 星期二 19:00:07
 */
@Log4j2
@Component
public class ContentExtProcessor extends DataBatchBase {
    @Autowired
    private MasterContentExtRepository masterRepository;
    @Autowired
    private SlaveContentExtRepository slaveRepository;

    @Scheduled(cron = "*/20 * * * * *")
    public void execute() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            JobExecution result = jobLauncher.run(contentExtDataHandle(), jobParameters);
            log.info(result);
        } catch (Exception e) {
            log.error("出现异常:", e);
        }
    }

    /**
     * 创建任务
     *
     * @return
     */
    private Job contentExtDataHandle() {
        // 创建作业构建器并初始化其作业存储库。 请注意，如果构建器用于创建 @Bean 定义，则作业名称和 bean 名称可能不同。
        return jobBuilderFactory.get("contentExtDataHandleJob")
                // start是JOB执行的第一个step
                .start(handleDataStep())
                // 下一个步骤
                // .next(xxxStep())
                // 设置了任务的执行监听
                .listener(executionListener)
                .build();

    }

    /**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     *
     * @return
     */
    private Step handleDataStep() {
        return stepBuilderFactory.get("getContentExtData")
                // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                // Chunk的机制(即每次读取一条数据，再处理一条数据，累积到一定数量后再一次性交给writer进行写入操作)
                .<MasterContentExt, SlaveContentExt>chunk(5)
                // 指定ItemReader
                .reader(dataReader())
                // 捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                .faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(3).skip(Exception.class)
                // .listener(new MyReadListener()) public class MyReadListener implements ItemReadListener<MasterContentExt>
                // 指定ItemProcessor
                .processor(dataProcessor())
                // 指定ItemWriter
                .writer(dataWriter())
                .faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(3).skip(Exception.class)
                // .listener(new MyWriteListener()) public class MyWriteListener implements ItemWriteListener<SlaveContentExt>
                .build();
    }

    /**
     * 分页读取原始数据
     *
     * @return
     */
    private RepositoryItemReader<MasterContentExt> dataReader() {
        // 读取数据,这里可以用JPA,JDBC,JMS 等方式 读入数据
        RepositoryItemReader<MasterContentExt> itemReader = new RepositoryItemReader<>();
        try {
            itemReader.setRepository(masterRepository);
            itemReader.setPageSize(10);
            itemReader.setMethodName("findAll");
            // 设置参数
            // List<String> params = new ArrayList<>(5);
            // params.add("i%");
            // itemReader.setArguments(params);
            // 设置排序方式
            Map<String, Sort.Direction> sortMap = new HashMap<>(2);
            // 排序字段中不能有_等特殊字符,spring-data中有分隔处理,很奇怪
            sortMap.put("id", Sort.Direction.ASC);
            itemReader.setSort(sortMap);
            itemReader.afterPropertiesSet();
            itemReader.setSaveState(true);
        } catch (Exception e) {
            log.error("读取Access数据出现异常:", e);
        }
        return itemReader;
    }

    /**
     * 将原始数据转换为目标数据
     *
     * @return
     */
    private ItemProcessor<MasterContentExt, SlaveContentExt> dataProcessor() {
        log.info("dataProcessor");
        return item -> {
            SlaveContentExt contentExt = new SlaveContentExt();
            BeanUtil.copyProperties(item, contentExt, true);
            contentExt.setContent_id(item.getId());
            contentExt.setRelease_date(LocalDateTime.now());
            return contentExt;
        };
    }

    /**
     * 写目标数据
     *
     * @return
     */
    private ItemWriter<SlaveContentExt> dataWriter() {
        log.info("dataWriter");
        RepositoryItemWriter<SlaveContentExt> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(slaveRepository);
        // 未指定方法默认调用saveAll方法执行批量插入,自定义方法是单个插入
        itemWriter.setMethodName("saveAndFlush");
        return itemWriter;
    }
}
