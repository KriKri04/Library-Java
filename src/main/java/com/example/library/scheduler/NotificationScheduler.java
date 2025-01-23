package com.example.library.scheduler;

import com.example.library.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 * * * * *")
    public void checkLoanDueDates() {
        notificationService.checkLoanDueDates();
        notificationService.updateOverdueLoans();
    }

}