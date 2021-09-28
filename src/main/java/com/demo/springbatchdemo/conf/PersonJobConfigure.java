package com.demo.springbatchdemo.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 【Person】任务配置
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/27 17:14
 */
@Slf4j
@Configuration
public class PersonJobConfigure {

    @Bean
    public Job personJob(JobBuilderFactory jobBuilderFactory, Step personStep) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .flow(personStep)
                .end()
                .build();
    }
}
