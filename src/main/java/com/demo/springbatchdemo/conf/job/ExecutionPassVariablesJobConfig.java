package com.demo.springbatchdemo.conf.job;

import com.demo.springbatchdemo.domain.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExecutionContext传递变量的测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/26 13:18
 */
@Slf4j
@Configuration
public class ExecutionPassVariablesJobConfig {

    @Bean
    public Job executionPassVariablesJob(JobBuilderFactory jobBuilderFactory,
                                         Step executionPassVariablesStep,
                                         JobExecutionListener executionPassVariablesJobExecutionListener) {
        return jobBuilderFactory.get("executionPassVariablesJob")
                .listener(executionPassVariablesJobExecutionListener)
                .start(executionPassVariablesStep)
                .build();
    }

    @Bean
    public JobExecutionListener executionPassVariablesJobExecutionListener(PersonEntity jobStepGlobalVariable) {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("PersonEntity: {}", jobStepGlobalVariable);
                // 设置Job全局变量
                jobExecution.getExecutionContext().put("demoJobCtxPerson", jobStepGlobalVariable);
            }

            @Override
            public void afterJob(JobExecution jobExecution) {

            }
        };
    }

    @Bean
    public PersonEntity jobStepGlobalVariable() {
        PersonEntity person = new PersonEntity();
        person.setId(1L);
        person.setFirstName("金华");
        person.setLastName("李");
        return person;
    }

    @Bean
    public StepExecutionListener executionPassVariablesExecutionListener(PersonEntity jobStepGlobalVariable) {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                // 将Step步骤的变量设置进去
                stepExecution.getExecutionContext().put("demoStepCtxPerson", jobStepGlobalVariable);
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                return null;
            }
        };
    }

    @Bean
    public Step executionPassVariablesStep(StepBuilderFactory stepBuilderFactory,
//                                @Value("#{jobExecution}") JobExecution jobExecution,
//                                @Value("#{stepExecution}") StepExecution stepExecution,
//                                @Value("#{jobParameters['time']}") Long timeParam,
                                StepExecutionListener executionPassVariablesExecutionListener) {
        return stepBuilderFactory.get("executionPassVariablesStep")
                .listener(executionPassVariablesExecutionListener)
                .tasklet((contribution, chunkContext) -> {
                    ExecutionContext stepCtx = contribution.getStepExecution().getExecutionContext();
                    PersonEntity stepCtxPerson = (PersonEntity) stepCtx.get("demoStepCtxPerson");

                    ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();
                    PersonEntity jobCtxPerson = (PersonEntity) jobCtx.get("demoJobCtxPerson");

                    log.info("Job全局变量PersonEntity: {}", jobCtxPerson);
                    log.info("Step全局变量Person: {}", stepCtxPerson);

                    return RepeatStatus.FINISHED;
                }).build();
    }
}
