package com.rippleinfo.batch.batch_test.configure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SampleBatchContextConfig {

    private static final Logger log = LoggerFactory.getLogger(SampleBatchContextConfig.class);

    @Autowired
    PlatformTransactionManager transactionManager;

    @Bean
    @ConfigurationProperties(prefix="spring.batch.datasource")
    public DataSource batchSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JobRepository jobRepository(PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(batchSource());
        factory.setTransactionManager(transactionManager);
        factory.setDatabaseType(String.valueOf(DatabaseType.MYSQL));
        factory.afterPropertiesSet();
        return  (JobRepository) factory.getObject();
    }

//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new ResourcelessTransactionManager();
//    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        /**
         * By default, jobLauncher uses a SyncTaskExecutor to run the job flow.
         * To make it run async, we need set and give a async executor.
         *
         ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
         executor.setCorePoolSize(2);
         executor.setMaxPoolSize(2);
         executor.setQueueCapacity(500);
         executor.setThreadNamePrefix("GithubLookup-");
         executor.initialize();
         simpleJobLauncher.setTaskExecutor();
         */

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Batch-");
        executor.initialize();
        simpleJobLauncher.setTaskExecutor(executor);
        return simpleJobLauncher;
    }

}
