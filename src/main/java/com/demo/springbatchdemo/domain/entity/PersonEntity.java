package com.demo.springbatchdemo.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * 【人物】实体类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/27 17:19
 */
@Data
public class PersonEntity {

    private Long id;

    private String firstName;

    private String lastName;

    @Override
    @SneakyThrows
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
