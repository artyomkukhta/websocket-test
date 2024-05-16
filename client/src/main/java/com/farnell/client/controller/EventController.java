package com.farnell.client.controller;

import com.farnell.client.model.EventDto;
import com.farnell.client.service.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/events")
public class EventController {

    private final WebSocketService socketService;

    public EventController(WebSocketService webSocketService) {
        this.socketService = webSocketService;
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto event) {
        try {
            socketService.sendObject(event);
        } catch (JsonProcessingException e) {
            // Обработка ошибки сериализации
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
}


