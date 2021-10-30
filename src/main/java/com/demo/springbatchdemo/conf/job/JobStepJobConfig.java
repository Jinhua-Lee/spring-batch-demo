package com.demo.springbatchdemo.conf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 嵌套Job，作为上层Job的Step配置
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/30 8:34
 */
@Slf4j
@Configuration
public class JobStepJobConfig {

    @Bean
    public Job jobStepJob(JobBuilderFactory jobBuilderFactory, Step jobStep) {
        return jobBuilderFactory.get("jobStepJob")
                .start(jobStep)
                .build();
    }

    /**
     * 引用Job，作为当前流程执行的Step
     *
     * @param stepBuilderFactory 步骤构建工厂
     * @return 步骤
     */
    @Bean
    public Step jobStep(StepBuilderFactory stepBuilderFactory, Job lateBindingJob,
                        SimpleJobParametersExtractor simpleJobParametersExtractor) {
        return stepBuilderFactory.get("jobStep")
                .job(lateBindingJob)
                // 如果子Job需要参数，则手动实现一个参数解析器来解析，流程会将这些参数给子Job
                .parametersExtractor(simpleJobParametersExtractor)
                .build();
    }

    @Component
    static class SimpleJobParametersExtractor implements JobParametersExtractor {

        @Override
        public JobParameters getJobParameters(Job job, StepExecution stepExecution) {
            return stepExecution.getJobParameters();
        }
    }
}
