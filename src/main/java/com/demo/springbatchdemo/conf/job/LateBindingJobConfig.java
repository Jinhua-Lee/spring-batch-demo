package com.demo.springbatchdemo.conf.job;

import com.demo.springbatchdemo.domain.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Job lateBindingJob(JobBuilderFactory jobBuilderFactory, Step lateBindingTasklet) {
        return jobBuilderFactory.get("lateBindingJob")
                .start(lateBindingTasklet)
                .build();
    }

    @Bean
    @StepScope
    public Step lateBindingTasklet(StepBuilderFactory stepBuilderFactory,
                                   @Value("#{jobParameters['time']}") Long timeParam) {
        return stepBuilderFactory.get("lateBindingTasklet")
                .tasklet((contribution, chunkContext) -> {
                    log.info("timeParam: {}", timeParam);
                    return RepeatStatus.FINISHED;
                }).build();
    }

//    @Bean
//    public Step lateBindingStep(StepBuilderFactory stepBuilderFactory, ItemReader<PersonEntity> personReadedr) {
//
//    }
//
//    @Bean
//    public ItemWriter<PersonEntity> personWriter() {
//        return new ListItemWriter<>();
//    }
}
