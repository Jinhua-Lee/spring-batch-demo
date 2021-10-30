package com.demo.springbatchdemo.conf.decider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 【双态随机】决策器
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/30 15:07
 */
@Slf4j
@Component
public class BinaryRandomDecider implements JobExecutionDecider {

    private final Random random = new Random();

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        int anInt = random.nextInt(2);
        FlowExecutionStatus fExecutionStatus;
        if (anInt % 2 == 0) {
            fExecutionStatus = new FlowExecutionStatus("α");
        } else {
            fExecutionStatus = new FlowExecutionStatus("β");
        }
        log.info("双态随机决策器：流程状态返回: {}", fExecutionStatus);
        return fExecutionStatus;
    }
}
