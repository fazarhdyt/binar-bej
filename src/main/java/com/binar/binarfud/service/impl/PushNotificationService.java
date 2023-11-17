package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushNotificationService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotification(NotificationMessage notificationMessage) {
        Message pushMessage = Message.builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(Notification.builder()
                        .setTitle(notificationMessage.getTitle())
                        .setBody(notificationMessage.getBody())
                        .setImage(notificationMessage.getImage())
                        .build())
                .putAllData(notificationMessage.getData())
                .build();

        try {
            firebaseMessaging.send(pushMessage);
            return "success sending notification";
        } catch (Exception e) {
            log.error("Error sending message: " + e.getMessage());
            return "Error sending message";
        }
    }
}
