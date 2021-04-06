package com.demo.task;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Author:   kinbridge
 * Date:     2021/4/6 22:34
 * Description:
 */
public class SchedulerTask {
    private int count=0;

    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }
}
