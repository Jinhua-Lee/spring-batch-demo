package com.demo.springbatchdemo.conf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * 动态决策配置类
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/17 19:52
 */
@Slf4j
@Configuration
public class DynamicDeciderConfig {

    @Bean
    public Job dynamicDeciderJob(JobBuilderFactory jobBuilderFactory, Step dStep1, Step dStep2, Step dStep3) {
        return jobBuilderFactory.get("dynamicDeciderJob")
                .start(dStep1)
                // 执行成功，则执行dStep2
                .on("COMPLETED").to(dStep2)
                // 出现错误，则执行dStep3
                .from(dStep1).on("FAILED").to(dStep3)
                .build()
                .build();
    }

    @Bean
    public Step dStep1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("dStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("dStep1...");
                    int c = new Random().nextInt(2);
                    if (c == 1) {
                        return RepeatStatus.FINISHED;
                    } else {
                        throw new RuntimeException("dStep1 failed");
                    }
                }).build();
    }

    @Bean
    public Step dStep2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("dStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("dStep2...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step dStep3(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("dStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("dStep3...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job dynamicDeciderJob2(JobBuilderFactory jobBuilderFactory,
                                  Step dStep1, Step dStep2, Step dStep3,
                                  JobExecutionDecider tripleDecider) {
        return jobBuilderFactory.get("dynamicDeciderJob2")
//                .flow(dStep1).build().build()
                .start(dStep1)
                .start(tripleDecider)
                .end().build();
    }
}
