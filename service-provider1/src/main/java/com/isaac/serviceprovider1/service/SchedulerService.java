package com.isaac.serviceprovider1.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SchedulerService {
    // cron = "[second1, second2, ...] [minute1/minute2] [hour1, hour2, ....] [day1 of month - day2 of month] [month] [day of week]"
    @Scheduled(cron = "0,30 * * * * *")
    public void scheduler1() {
        System.out.println("scheduled operation" + new Date());
    }

    public void testHello(String s){};
}
