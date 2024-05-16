package com.farnell.server.controller;

import com.farnell.server.controller.dto.EventDto;
import com.farnell.server.model.Event;
import com.farnell.server.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class EventController {
    private final EventService eventService;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);


    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @MessageMapping("/send-event")
    @SendTo("/topic/public")
    public EventDto receiveEvent(@Payload EventDto eventDto) {
        log.info("Received Event: {}", eventDto.toString());
        Event event = Event.builder()
                .eventType(eventDto.getEventType())
                .eventId(eventDto.getEventId())
                .timestamp(eventDto.getTimestamp())
                .description(eventDto.getDescription())
                .build();
         eventService.save(event);
         return eventDto;
    }

    /*@MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }*/

}
