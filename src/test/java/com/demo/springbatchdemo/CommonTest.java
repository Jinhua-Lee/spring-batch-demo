package com.demo.springbatchdemo;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * 通用测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/24 22:14
 */
public class CommonTest {

    @Test
    public void testBigDecimalException() {
        BigDecimal subtract = BigDecimal.ONE.divide(BigDecimal.ZERO);
        System.out.println(subtract);
    }
}
