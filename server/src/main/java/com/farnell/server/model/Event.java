package com.farnell.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event extends BaseEntity {
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
}

