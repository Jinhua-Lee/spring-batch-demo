package com.demo.springbatchdemo.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Todo
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/10/17 20:48
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AbstractDynamicDeciderTest extends AbstractDynamicDeciderTask {

    @Test
    @SneakyThrows
    public void testSimple() {
        JobExecution run = jobLauncher.run(this.dynamicDeciderJob, new JobParameters());
        log.info("exitStatus = {}", run.getExitStatus());
    }

    @Test
    @SneakyThrows
    public void testDynamicDecider() {

    }
}
