package com.demo.springbatchdemo.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务启动单元测试类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/28 18:13
 */
@Slf4j
@SpringBootTest
public class PersonScheduledTaskTest extends PersonScheduledTask {

    @Test
    @SneakyThrows
    public void testManualExec() {
        final int mapSize = 8;
        Map<String, JobParameter> jobParameterMap = new HashMap<>(mapSize);
//        jobParameterMap.put("demo", new JobParameter(2L, true));

        JobExecution run = super.jobLauncher.run(this.personJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }
}