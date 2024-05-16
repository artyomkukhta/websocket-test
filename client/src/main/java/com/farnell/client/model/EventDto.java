package com.farnell.client.model;

import java.time.LocalDateTime;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class EventDto {
    private String eventId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    public enum EventType {
        ALARM,
        WARN,
        INFO
    }

    private LocalDateTime timestamp;
    private String description;

    // Getters and setters
}