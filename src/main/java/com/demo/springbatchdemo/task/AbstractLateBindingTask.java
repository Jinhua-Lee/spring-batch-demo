package com.demo.springbatchdemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 【动态绑定】
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/26 16:25
 */
@Slf4j
@Component
public abstract class AbstractLateBindingTask {

    protected JobLauncher jobLauncher;
    protected Job lateBindingJob;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setLateBindingJob(Job lateBindingJob) {
        this.lateBindingJob = lateBindingJob;
    }
}