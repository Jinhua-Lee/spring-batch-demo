package com.demo.springbatchdemo.conf.decider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 【三态随机】决策器
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/19 16:01
 */
@Slf4j
@Component
public class TripleRandomDecider implements JobExecutionDecider {

    private final Random random = new Random();

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        int anInt = random.nextInt(3);
        FlowExecutionStatus fExecutionStatus;
        if (anInt % 3 == 0) {
            fExecutionStatus = new FlowExecutionStatus("alpha");
        } else if (anInt % 3 == 1) {
            fExecutionStatus = new FlowExecutionStatus("beta");
        } else {
            fExecutionStatus = new FlowExecutionStatus("gama");
        }
        log.info("三态随机决策器：流程状态返回: {}", fExecutionStatus);
        return fExecutionStatus;
    }
}
