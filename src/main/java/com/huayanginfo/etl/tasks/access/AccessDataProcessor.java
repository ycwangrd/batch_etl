package com.huayanginfo.etl.tasks.access;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.huayanginfo.etl.config.DataBatchBase;
import com.huayanginfo.etl.model.entity.Access;
import kohgylw.kcnamer.core.KCNamer;
import kohgylw.kcnamer.core.NameLength;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月20日 星期五 10:15:34
 */
@Log4j2
@Component
public class AccessDataProcessor extends DataBatchBase {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void execute() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            JobExecution result = jobLauncher.run(accessDataHandle(), jobParameters);
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
    private Job accessDataHandle() {
        // 创建作业构建器并初始化其作业存储库。 请注意，如果构建器用于创建 @Bean 定义，则作业名称和 bean 名称可能不同。
        return jobBuilderFactory.get("accessDataHandle")
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
        return stepBuilderFactory.get("getData")
                // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                .<Access, Access>chunk(5)
                // 捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                .faultTolerant().retryLimit(1).retry(Exception.class).skipLimit(1).skip(Exception.class)
                // 指定ItemReader
                .reader(dataReader())
                // 指定ItemProcessor
                .processor(dataProcessor())
                // 指定ItemWriter
                .writer(dataWriter())
                .build();
    }

    /**
     * 分页读取数据
     *
     * @return
     */
    private JpaPagingItemReader<Access> dataReader() {
        // 读取数据,这里可以用JPA,JDBC,JMS 等方式 读入数据
        JpaPagingItemReader<Access> reader = new JpaPagingItemReader<>();
        // 这里选择JPA方式读数据 一个简单的 native SQL
        String sqlQuery = "SELECT * FROM access";
        try {
            JpaNativeQueryProvider<Access> queryProvider = new JpaNativeQueryProvider<>();
            queryProvider.setSqlQuery(sqlQuery);
            queryProvider.setEntityClass(Access.class);
            queryProvider.afterPropertiesSet();
            reader.setEntityManagerFactory(defaultEmf);
            reader.setPageSize(2);
            reader.setQueryProvider(queryProvider);
            reader.afterPropertiesSet();
            // 所有ItemReader和ItemWriter实现都会在ExecutionContext提交之前将其当前状态存储在其中,如果不希望这样做,可以设置setSaveState(false)
            reader.setSaveState(true);
        } catch (Exception e) {
            log.error("读取Access数据出现异常:", e);
        }
        return reader;
    }

    private ItemProcessor<Access, Access> dataProcessor() {
        System.out.println("dataProcessor");
        KCNamer kcNamer = new KCNamer();
        return item -> {
            // 空缺字段的补充
            if (StringUtils.equalsIgnoreCase("*", item.getShopName())) {
                item.setShopName(kcNamer.getRandomFemaleName());
            }
            if (StringUtils.equalsIgnoreCase("*", item.getCategoryName())) {
                item.setCategoryName(kcNamer.getRandomMaleName());
            }
            if (StringUtils.isBlank(item.getDescription())) {
                item.setDescription(kcNamer.getComRandomName());
            }
            item.setBrandName(kcNamer.getRandomFemaleName(NameLength.FOUR));
            item.setOmit(kcNamer.getRandomMaleName(NameLength.THREE));
            item.setShopId(RandomUtils.nextInt() + "");
            item.setCreateTime(item.getUpdateTime());
            item.setUpdateTime(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN));
            log.info("processor data : " + item);  //模拟  假装处理数据,这里处理就是打印一下
            return item;
        };
    }

    private ItemWriter<Access> dataWriter() {
        System.out.println("dataWriter");
        JdbcBatchItemWriter<Access> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setItemPreparedStatementSetter((ItemPreparedStatementSetter) (item, ps) -> {
            Access access = (Access) item;
            ps.setInt(1, access.getId());
            ps.setString(2, access.getUsername());
            ps.setString(3, access.getShopName());
            ps.setString(4, access.getCategoryName());
            ps.setString(5, access.getDescription());
            ps.setBoolean(6, access.isDeleteStatus());
            ps.setString(7, access.getBrandName());
            ps.setString(8, access.getShopId());
            ps.setString(9, access.getOmit());
            ps.setString(10, access.getUpdateTime());
            ps.setString(11, access.getCreateTime());
            // 更新的参数
            ps.setString(12, access.getUsername());
            ps.setString(13, access.getShopName());
            ps.setString(14, access.getCategoryName());
            ps.setString(15, access.getDescription());
            ps.setBoolean(16, access.isDeleteStatus());
            ps.setString(17, access.getBrandName());
            ps.setString(18, access.getShopId());
            ps.setString(19, access.getOmit());
            ps.setString(20, access.getUpdateTime());
            ps.setString(21, access.getCreateTime());
        });
        writer.setSql("INSERT INTO `access_bak`" +
                " (`id`, `username`, `shop_name`, `category_name`, `description`, `delete_status`, `brand_name`, `shop_id`, `omit`, `update_time`, `create_time`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                " ON DUPLICATE KEY update" +
                " `username`=?, `shop_name`=?, `category_name`=?, `description`=?, `delete_status`=?, `brand_name`=?, `shop_id`=?, `omit`=?, `update_time`=?, `create_time`=?");
        writer.setDataSource(dataSource);
        return writer;
    }
}
