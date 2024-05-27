package com.farnell.server.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private String eventId;
    private EventType eventType;
    private LocalDateTime timestamp;
    private String description;
    private String clientId;
    public enum EventType {
        ALARM,
        WARN,
        INFO
    }
}
