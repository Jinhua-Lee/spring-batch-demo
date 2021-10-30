package com.demo.springbatchdemo.conf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/19 14:43
 */
@Slf4j
@Configuration
public class FlowConfig {

    @Bean
    public Job randomDeciderJob(JobBuilderFactory jobBuilderFactory, Flow randomDeciderFlow) {
        return jobBuilderFactory.get("randomDeciderJob")
                .start(randomDeciderFlow)
                .end().build();
    }

    @Bean
    public Flow randomDeciderFlow(Step stepA, Step stepB, Step stepC, Step stepD, Step stepE,
                                  JobExecutionDecider binaryRandomDecider, JobExecutionDecider tripleRandomDecider) {
        return new FlowBuilder<Flow>("randomDeciderFlow")
                .start(stepA).next(binaryRandomDecider)
                .on("α").to(stepB)
                .on("β").to(stepC)
                .from(stepB).next(tripleRandomDecider).on("alpha").to(stepD)
                .on("beta").to(stepE)
                .on("gama").to(stepC)
                .build();
    }

    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepA")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletA...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepB(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepB")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletB...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepC(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepC")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletC...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepD(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepD")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletD...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepE(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepE")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletE...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
