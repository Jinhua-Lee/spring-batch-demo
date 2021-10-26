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
 * Todo
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/26 16:29
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LateBindingTaskTest extends AbstractLateBindingTask {

    @Test
    @SneakyThrows
    public void execute() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(1L, true));
        JobExecution run = super.jobLauncher.run(lateBindingJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }
}
