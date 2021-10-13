package com.demo.springbatchdemo.conf;

import com.demo.springbatchdemo.domain.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 【Person2】Job的功能：<p>&emsp;
 * 1) 从person表读数据，简单处理，写到person2表<p>&emsp;
 * 2) 读写功能利用mybatis-spring中的支持完成
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/13 20:03
 */
@Slf4j
@Configuration
public class Person2JobConfigure {

    @Bean
    public MyBatisPagingItemReader<PersonEntity> myBatisPagingItemReader(DataSource dataSource) {
        return null;
    }

}
