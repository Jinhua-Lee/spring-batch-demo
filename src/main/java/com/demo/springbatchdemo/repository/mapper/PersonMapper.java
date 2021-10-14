package com.demo.springbatchdemo.repository.mapper;

import com.demo.springbatchdemo.domain.entity.PersonEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/14 9:35
 */
@Mapper
public interface PersonMapper {

    /**
     * 分页读，分页参数由batch控制
     *
     * @param pageParamMap 分页入参
     * @return 当页数据
     */
    List<PersonEntity> queryByPage(Map<String, Object> pageParamMap);
}
