package com.demo.springbatchdemo.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/10/28 16:26
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JobTest extends AbstractTask {

    @Test
    @SneakyThrows
    public void testPersonTask() {
        final int mapSize = 8;
        Map<String, JobParameter> jobParameterMap = new HashMap<>(mapSize);
//        jobParameterMap.put("demo", new JobParameter(2L, true));

        JobExecution run = super.jobLauncher.run(this.personJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testPersonTask2() {
        JobExecution run = super.jobLauncher.run(this.person2Job, new JobParameters());
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testDynamicDecider() {
        JobExecution run = jobLauncher.run(this.dynamicDeciderJob, new JobParameters());
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testRandomDeciderFlowJob() {
        JobExecution run = jobLauncher.run(this.randomDeciderJob, new JobParameters());
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testExecutionPassVariables() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(1L, false));
        JobExecution run = super.jobLauncher.run(executionPassVariablesJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testLateBinding() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(1L, false));
        JobExecution run = super.jobLauncher.run(lateBindingJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testJobStep() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        // 该参数将传递给子Job
        jobParameterMap.put("time", new JobParameter(1L, false));
        JobExecution run = super.jobLauncher.run(jobStepJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }
}
