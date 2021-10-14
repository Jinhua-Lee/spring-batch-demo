package com.demo.springbatchdemo.conf;

import com.demo.springbatchdemo.domain.entity.LowerCasePersonEntity;
import com.demo.springbatchdemo.domain.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 【Person2】步骤的配置
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/13 20:03
 */
@Slf4j
@Configuration
public class Person2StepConfigure {

    @Bean
    public Step person2Step(StepBuilderFactory stepBuilderFactory,
                            ItemReader<PersonEntity> personPagingItemReader,
                            ItemWriter<LowerCasePersonEntity> lowerCasePersonEntityItemWriter,
                            ItemProcessor<PersonEntity, LowerCasePersonEntity> toLowerCaseProcess) {
        return stepBuilderFactory.get("person2Step")
                .<PersonEntity, LowerCasePersonEntity>chunk(2)
                .reader(personPagingItemReader)
                .writer(lowerCasePersonEntityItemWriter)
                .processor(toLowerCaseProcess)
                .build();
    }

    @Bean
    public ItemReader<PersonEntity> personPagingItemReader(
            SqlSessionFactory customizedMapperLocationSqlSessionFactory) {
        Map<String, Object> paramsMap = new HashMap<>(8);
        paramsMap.put("_pagesize", 2);
        paramsMap.put("_page", 1);
        return new MyBatisPagingItemReaderBuilder<PersonEntity>()
                .sqlSessionFactory(customizedMapperLocationSqlSessionFactory)
                .queryId("com.demo.springbatchdemo.repository.mapper.PersonMapper.queryByPage")
                .parameterValues(paramsMap)
                .build();
    }

    @Bean
    public ItemProcessor<PersonEntity, LowerCasePersonEntity> toLowerCaseProcess() {
        return personEntity -> LowerCasePersonEntity.builder()
                .firstName(personEntity.getFirstName().toLowerCase())
                .lastName(personEntity.getLastName().toLowerCase())
                .build();
    }

    @Bean
    public ItemWriter<LowerCasePersonEntity> lowerCasePersonBatchIemWriter(
            SqlSessionFactory customizedMapperLocationSqlSessionFactory) {
        return new MyBatisBatchItemWriterBuilder<LowerCasePersonEntity>()
                .sqlSessionFactory(customizedMapperLocationSqlSessionFactory)
                // 注意，这里的构造是单条插入，chunk机制会使得它达到数量后再批量插入
                .statementId("com.demo.springbatchdemo.repository.mapper.LowerCasePersonMapper.batchInsert")
                .build();
    }
}
