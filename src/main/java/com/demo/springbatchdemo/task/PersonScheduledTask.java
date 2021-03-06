package com.demo.springbatchdemo.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 【Person】转存单次任务
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/27 20:09
 */
@Slf4j
@Component
public class PersonScheduledTask extends AbstractTask {

    @SneakyThrows
    @Scheduled(cron = "${task.cron.person}")
    public void execute() {
        final int mapSize = 8;
        Map<String, JobParameter> jobParameterMap = new HashMap<>(mapSize);
//        jobParameterMap.put("demo", new JobParameter(2L, true));

        JobExecution run = this.jobLauncher.run(this.personJob, new JobParameters(jobParameterMap));
        log.info("exitStatus = {}", run.getExitStatus());
    }
}
