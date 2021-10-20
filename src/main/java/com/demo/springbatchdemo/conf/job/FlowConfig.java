package com.demo.springbatchdemo.conf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
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
    public Step stepOne(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepOne")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletOne...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepTwo(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepTwo")
                .tasklet((contribution, chunkContext) -> {
                    log.info("taskletTwo...");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Flow preProcessingFlow(Step stepOne, Step stepTwo) {
        return new FlowBuilder<Flow>("preProcessingFlow")
                .start(stepOne)
                // 仅在第一步完成的情况下执行第二步
                .on("COMPLETED").to(stepTwo)
                .build();
    }
}
