package com.farnell.server.service;

import com.farnell.server.controller.dto.SettingsDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private final SimpMessagingTemplate messagingTemplate;

    public SettingsService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void send(String userId, SettingsDto settings) {
        messagingTemplate.convertAndSendToUser(userId,"/topic/private-settings", settings);
    }

    public void sendAll(SettingsDto settings) {
        messagingTemplate.convertAndSend("/topic/public-settings", settings);
    }

}
