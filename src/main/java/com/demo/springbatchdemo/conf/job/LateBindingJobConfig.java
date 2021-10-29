package com.demo.springbatchdemo.conf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 【后期绑定】的Job配置
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/28 16:18
 */
@Slf4j
@Configuration
public class LateBindingJobConfig {

    @Bean
    public Job lateBindingJob(JobBuilderFactory jobBuilderFactory, Step lateBindingTaskletStep) {
        return jobBuilderFactory.get("lateBindingJob")
                .start(lateBindingTaskletStep)
                .build();
    }

    @Bean
    public Step lateBindingTaskletStep(StepBuilderFactory stepBuilderFactory,
                                       @Qualifier(value = "lateBindingTaskletAnonymous")
                                               Tasklet lateBindingTasklet) {
        return stepBuilderFactory.get("lateBindingTasklet")
                .tasklet(lateBindingTasklet).build();
    }

    @Bean(name = "lateBindingTaskletAnonymous")
    @StepScope
    public Tasklet lateBindingTasklet(@Value("#{jobParameters['time']}") Long timeParam) {
        // 简单的过程，直接通过匿名方式
        return (contribution, chunkContext) -> {
            log.info("timeParam: {}", timeParam);
            return RepeatStatus.FINISHED;
        };
    }

    /**
     * 复杂的过程，最好还是直接定义类来完成
     */
    @Component
    @StepScope
    static class LateBindingTasklet implements Tasklet {

        /**
         * 用作保存JobParameter的参数
         */
        private final Long time;

        /**
         * 晚期绑定注入属性
         *
         * @param time JobParameters的一个参数
         */
        public LateBindingTasklet(@Value("#{jobParameters['time']}") Long time) {
            this.time = time;
        }

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
            log.info("timeParam: {}", this.time);
            return RepeatStatus.FINISHED;
        }
    }
}
