package com.demo.springbatchdemo.repository.mapper;

import com.demo.springbatchdemo.domain.entity.LowerCasePersonEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/14 11:08
 */
@Mapper
public interface LowerCasePersonMapper {

    /**
     * 单条插入
     *
     * @param lowerCasePerson 待插入实体
     * @return 受影响行数
     */
    int batchInsert(@Param(value = "lowerCasePerson") LowerCasePersonEntity lowerCasePerson);
}
