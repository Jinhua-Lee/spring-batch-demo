package com.demo.springbatchdemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 动态决策类
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/17 20:33
 */
@Slf4j
@Component
public abstract class DynamicDeciderTask {

    protected JobLauncher jobLauncher;
    protected Job dynamicDeciderJob;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setDynamicDeciderJob(Job dynamicDeciderJob) {
        this.dynamicDeciderJob = dynamicDeciderJob;
    }
}
