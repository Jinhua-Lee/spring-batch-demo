package com.demo.springbatchdemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/28 15:57
 */
@Slf4j
@Component
public abstract class AbstractTask {

    protected JobLauncher jobLauncher;
    protected Job personJob;
    protected Job person2Job;
    protected Job dynamicDeciderJob;
    protected Job executionPassVariablesJob;
    protected Job lateBindingJob;
    protected Job jobStepJob;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setPersonJob(Job personJob) {
        this.personJob = personJob;
    }

    @Autowired
    public void setPerson2Job(Job person2Job) {
        this.person2Job = person2Job;
    }

    @Autowired
    public void setDynamicDeciderJob(Job dynamicDeciderJob) {
        this.dynamicDeciderJob = dynamicDeciderJob;
    }

    @Autowired
    public void setExecutionPassVariablesJob(Job executionPassVariablesJob) {
        this.executionPassVariablesJob = executionPassVariablesJob;
    }

    @Autowired
    public void setLateBindingJob(Job lateBindingJob) {
        this.lateBindingJob = lateBindingJob;
    }

    @Autowired
    public void setJobStepJob(Job jobStepJob) {
        this.jobStepJob = jobStepJob;
    }
}
