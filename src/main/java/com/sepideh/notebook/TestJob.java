package com.sepideh.notebook;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableAsync
public class TestJob {

//    @Scheduled(fixedDelay = 1000)
//    public void scheduleFixedDelayTask() {
//        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
//    }

//    @Async
//    @Scheduled(fixedRate = 1000)
//    public void scheduleFixedRateTask() {
//        System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
//    }

    @Scheduled(cron = "0 56 10 21 * ?")    // second - minute - hour - day of the month - month - day of the week
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks using cron jobs - " + now);
    }

}
