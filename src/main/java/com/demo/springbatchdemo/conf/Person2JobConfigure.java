package com.demo.springbatchdemo.conf;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

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

}
