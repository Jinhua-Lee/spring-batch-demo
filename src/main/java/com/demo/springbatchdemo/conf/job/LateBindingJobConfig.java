package com.demo.springbatchdemo.conf.job;

import com.demo.springbatchdemo.domain.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 后期绑定的Demo测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/26 13:18
 */
@Slf4j
@Configuration
public class LateBindingJobConfig {

    @Bean
    public Job lateBindingJob(JobBuilderFactory jobBuilderFactory,
                              Step laterBindingStep,
                              JobExecutionListener lateBindingJobExecutionListener) {
        return jobBuilderFactory.get("lateBindingJob")
                .listener(lateBindingJobExecutionListener)
                .start(laterBindingStep)
                .build();
    }

    @Bean
    @StepScope
    public JobExecutionListener lateBindingJobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                PersonEntity person = new PersonEntity();
                person.setId(1L);
                person.setFirstName("金华");
                person.setLastName("李");
                jobExecution.getExecutionContext().put("demoJobCtxPerson", person);
            }

            @Override
            public void afterJob(JobExecution jobExecution) {

            }
        };
    }

    @Bean
    @StepScope
    public StepExecutionListener lateBindingStepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                // 将Step步骤的变量设置进去
                stepExecution.getExecutionContext().put("demoStepCtx", "hello stepCtx");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                return null;
            }
        };
    }

    @Bean
    @StepScope
    public Step laterBindingStep(StepBuilderFactory stepBuilderFactory,
                                 @Value("#{jobExecution}") JobExecution jobExecution,
                                 @Value("#{stepExecution}") StepExecution stepExecution,
                                 @Value("#{jobParameters['time']}") Long timeParam,
                                 StepExecutionListener laterBindingStepExecutionListener) {
        return stepBuilderFactory.get("laterBindingStep")
                .listener(laterBindingStepExecutionListener)
                .tasklet((contribution, chunkContext) -> {
                    ExecutionContext stepCtx = stepExecution.getExecutionContext();
                    String stepCtxStr = (String) stepCtx.get("demoStepCtx");

                    ExecutionContext jobCtx = jobExecution.getExecutionContext();
                    PersonEntity jobCtxPerson = (PersonEntity) jobCtx.get("demoJobCtxPerson");

                    log.info("Job全局变量PersonEntity: {}", jobCtxPerson);
                    log.info("Step全局变量String: {}", stepCtxStr);
                    log.info("全局入参 time : {}", timeParam);

                    return RepeatStatus.FINISHED;
                }).build();
    }
}
