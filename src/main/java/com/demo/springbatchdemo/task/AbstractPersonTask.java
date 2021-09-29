package com.demo.springbatchdemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/29 9:40
 */
@Slf4j
@Component
public abstract class AbstractPersonTask {

    protected JobLauncher jobLauncher;
    protected Job personJob;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setPersonJob(Job personJob) {
        this.personJob = personJob;
    }
}
