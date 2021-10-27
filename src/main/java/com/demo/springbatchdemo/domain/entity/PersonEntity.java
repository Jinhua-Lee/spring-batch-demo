package com.demo.springbatchdemo.domain.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * 【人物】实体类
 * batch信任的序列化类型中不包含自定义的类型，所以，如果需要保证类型
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/27 17:19
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class PersonEntity implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    @Override
    @SneakyThrows
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
