package com.demo.springbatchdemo.conf.job;

import com.demo.springbatchdemo.domain.entity.PersonEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

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

    @Value(value = "classpath*:csv/inputPerson*.csv")
    private Resource[] personResources;

    @Bean
    public Job personJob(JobBuilderFactory jobBuilderFactory, Step personStep) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .flow(personStep)
                .end()
                .build();
    }

    @Bean
    public Step personStep(StepBuilderFactory stepBuilderFactory,
                           ItemReader<PersonEntity> multiResourceItemReader, ItemWriter<PersonEntity> personWriter,
                           ItemProcessor<PersonEntity, PersonEntity> personProcessor) {
        return stepBuilderFactory.get("personStep")
                .<PersonEntity, PersonEntity>chunk(2)
                .reader(multiResourceItemReader)
                .writer(personWriter)
                .processor(personProcessor)
                .build();
    }

    @Bean
    public ItemReader<PersonEntity> multiResourceItemReader(FlatFileItemReader<PersonEntity> personReader) {
        MultiResourceItemReader<PersonEntity> multiPersonReader = new MultiResourceItemReader<>();
        multiPersonReader.setResources(this.personResources);
        multiPersonReader.setDelegate(personReader);
        return multiPersonReader;
    }

    @Bean
    public FlatFileItemReader<PersonEntity> personReader() {
        FlatFileItemReader<PersonEntity> reader = new FlatFileItemReader<>();
        // 跳过csv文件第一行，为表头
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<PersonEntity>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        // 字段名
                        setNames("id", "firstName", "lastName");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonEntity>() {
                    {
                        // 转换化后的目标类
                        setTargetType(PersonEntity.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public ItemProcessor<PersonEntity, PersonEntity> personProcessor() {
        return person -> {
            log.info("before: {}", new ObjectMapper().writeValueAsString(person));
            person.setLastName(person.getLastName().toUpperCase());
            log.info("after: {}", new ObjectMapper().writeValueAsString(person));
            return person;
        };
    }

    @Bean
    public ItemWriter<PersonEntity> personWriter(DataSource dataSource) {
        JdbcBatchItemWriter<PersonEntity> personJdbcWriter = new JdbcBatchItemWriter<>();
        personJdbcWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        String insert = "insert into person(first_name, last_name)" +
                "values(:firstName, :lastName)";
        personJdbcWriter.setDataSource(dataSource);
        personJdbcWriter.setSql(insert);
        return personJdbcWriter;
    }

}
