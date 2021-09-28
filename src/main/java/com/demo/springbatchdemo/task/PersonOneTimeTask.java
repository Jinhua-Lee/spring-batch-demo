package com.demo.springbatchdemo.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 【Person】转存单次任务
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/27 20:09
 */
@Slf4j
@Component
public class PersonOneTimeTask {

    private JobLauncher jobLauncher;
    private Job personJob;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setPersonJob(Job personJob) {
        this.personJob = personJob;
    }

    @SneakyThrows
    @Scheduled(cron = "${task.person.cron}")
    public void executeOneTime() {
        JobExecution run = this.jobLauncher.run(this.personJob, new JobParameters());
        log.info("exitStatus = {}", run.getExitStatus());
    }
}
