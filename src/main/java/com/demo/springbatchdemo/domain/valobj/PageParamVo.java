package com.demo.springbatchdemo.domain.valobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/14 11:49
 */
@Data
public class PageParamVo {

    @JsonProperty("_pagesize")
    private Integer limit;

    @JsonProperty("_skiprows")
    private Integer offset;
}
