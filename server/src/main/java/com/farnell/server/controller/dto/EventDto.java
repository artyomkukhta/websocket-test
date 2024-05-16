package com.farnell.server.controller.dto;

import com.farnell.server.model.BaseEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private String eventId;
    @Enumerated(EnumType.STRING)
    private com.farnell.server.model.Event.EventType eventType;
    public enum EventType {
        ALARM,
        WARN,
        INFO
    }
    private LocalDateTime timestamp;
    private String description;
}
