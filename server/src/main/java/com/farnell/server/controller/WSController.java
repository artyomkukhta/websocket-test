package com.farnell.server.controller;

import com.farnell.server.controller.dto.EventDto;
import com.farnell.server.model.Event;
import com.farnell.server.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
public class WSController {
    private final EventService eventService;
    private static final Logger log = LoggerFactory.getLogger(WSController.class);
    private final SimpMessagingTemplate messagingTemplate;

    public WSController(EventService eventService, SimpMessagingTemplate messagingTemplate) {
        this.eventService = eventService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/receive-private-event")
    // @SendToUser("/topic/private-event")
    public EventDto receiveEvent(@Payload EventDto eventDto, StompHeaderAccessor headerAccessor, Principal principal) {
        log.info("Received Event: {}", eventDto.toString());
        log.info("Session ID: {}", headerAccessor.getSessionId());

        Event event = Event.builder()
                .eventType(Event.EventType.valueOf(eventDto.getEventType().toString()))
                .eventId(eventDto.getEventId())
                .timestamp(eventDto.getTimestamp())
                .description(eventDto.getDescription())
                .build();
        eventService.save(event);

        messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/private-event", eventDto);
        return eventDto;
    }


}
