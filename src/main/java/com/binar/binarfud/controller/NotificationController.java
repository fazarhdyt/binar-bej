package com.binar.binarfud.controller;

import com.binar.binarfud.model.NotificationMessage;
import com.binar.binarfud.service.impl.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    PushNotificationService pushNotificationService;

    @PostMapping
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) {
        return pushNotificationService.sendNotification(notificationMessage);
    }
}
