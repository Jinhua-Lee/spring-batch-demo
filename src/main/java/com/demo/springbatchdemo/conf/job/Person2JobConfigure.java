package com.demo.springbatchdemo.conf.job;

import com.demo.springbatchdemo.domain.entity.LowerCasePersonEntity;
import com.demo.springbatchdemo.domain.entity.PersonEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 【Person2】Job的功能：<p>&emsp;
 * 1) 从person表读数据，简单处理，写到另一个lower_case_person表<p>&emsp;
 * 2) 读写功能利用mybatis-spring中的支持完成
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/13 20:03
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"com.demo.springbatchdemo.repository.mapper"},
        sqlSessionFactoryRef = "customizedMapperLocationSqlSessionFactory")
public class Person2JobConfigure {

    @Bean
    @SneakyThrows
    public SqlSessionFactory customizedMapperLocationSqlSessionFactory(DataSource dataSource) {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    public Job person2Job(JobBuilderFactory jobBuilderFactory, Step person2Step) {
        return jobBuilderFactory.get("person2Job")
                .flow(person2Step)
                .end()
                .build();
    }

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
