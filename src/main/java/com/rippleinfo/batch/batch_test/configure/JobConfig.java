package com.rippleinfo.batch.batch_test.configure;

import com.rippleinfo.batch.batch_test.model.ListStore;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JobConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private ListStore store;

    @Autowired
    public JobConfig(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory, ListStore store ){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.store = store;
    }


    @Bean
    public ItemReader myReader() {
        return new ListItemReader(store.getStore());
    }

    @Bean
    public ItemProcessor  myProcessor() {
        return new ItemProcessor<Object, Object>() {
            @Override
            public String process(Object item) throws Exception {
                System.out.println("---------------------------");
                return "_" + (String)item;
            }
        };
    }

    @Bean
    public ItemWriter myWriter(){
        return new ItemWriter<Object>() {
            @Override
            public void write(List<? extends Object> items) throws Exception {
                System.out.println("******************************");
                for(Object item : items){
                    //simulate a long time job
                    Thread.sleep(1000);
                    System.out.println(item);
                }
            }
        };
    }

    @Bean
    public Job job() throws Exception {
        return this.jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .allowStartIfComplete(true)
                .chunk(3)
                .reader(myReader())
                .processor(myProcessor())
                .writer(myWriter())
                .build();
    }

}
