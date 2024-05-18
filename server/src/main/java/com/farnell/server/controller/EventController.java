package com.farnell.server.controller;

import com.farnell.server.controller.dto.EventDto;
import com.farnell.server.model.Event;
import com.farnell.server.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
public class EventController {
    private final EventService eventService;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentMap<String, String> sessionClientIdMap = new ConcurrentHashMap<>();

    public EventController(EventService eventService, SimpMessagingTemplate messagingTemplate) {
        this.eventService = eventService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send-event")
    public void receiveEvent(@Payload EventDto eventDto, @Header("simpSessionId") String sessionId) {
        log.info("Received Event: {}", eventDto.toString());
        log.info("Session ID: {}", sessionId);
        sessionClientIdMap.put(sessionId, eventDto.getClientId());

        Event event = Event.builder()
                .eventType(Event.EventType.valueOf(eventDto.getEventType().toString()))
                .eventId(eventDto.getEventId())
                .timestamp(eventDto.getTimestamp())
                .description(eventDto.getDescription())
                .build();
        eventService.save(event);

        messagingTemplate.convertAndSendToUser(sessionId, "/topic/specific-user", eventDto);

        //messagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/public", eventDto);
        //messagingTemplate.convertAndSend("/topic/public", eventDto);
    }
  /*  @MessageMapping("/send-event-to-client")
    public void sendEventToSpecificClient(@Payload EventDto eventDto, StompHeaderAccessor headerAccessor) {
        String targetClientId = eventDto.getClientId();  // Идентификатор клиента, которому нужно отправить сообщение

        sessionClientIdMap.forEach((sessionId, clientId) -> {
            if (clientId.equals(targetClientId)) {
                messagingTemplate.convertAndSendToUser(sessionId, "/user/queue/private", eventDto);

            }
        });
    }*/
}
