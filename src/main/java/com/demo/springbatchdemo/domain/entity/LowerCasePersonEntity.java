package com.demo.springbatchdemo.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

/**
 * 【小写的人物】实体类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/14 10:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowerCasePersonEntity {

    private Long id;

    private String firstName;

    private String lastName;

    @Override
    @SneakyThrows
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }

}
