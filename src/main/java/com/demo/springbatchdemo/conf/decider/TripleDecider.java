package com.demo.springbatchdemo.conf.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/19 16:01
 */
@Component
public class TripleDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
        if (stepExecution == null) {
            // 若还未开始步骤的执行，则执行默认的第一步
            return new FlowExecutionStatus(Objects.requireNonNull(jobParameters.getString("flowDeciderKey")));
        }
        return null;
    }
}
