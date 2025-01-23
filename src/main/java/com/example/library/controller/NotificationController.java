package com.example.library.controller;

import com.example.library.model.Notification;
import com.example.library.security.SecurityUtil;
import com.example.library.service.AppUserService;
import com.example.library.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AppUserService appUserService;

    @ModelAttribute("notifications")
    public List<Notification> addNotificationsToModel() {
        System.out.println("addNotificationsToModel called");
        try {
            return notificationService.getNotifications(
                    appUserService.findAppUserByUsername(SecurityUtil.getCurrentUsername())
            );
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
