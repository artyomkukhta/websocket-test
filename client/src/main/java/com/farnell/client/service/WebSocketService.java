package com.farnell.client.service;

import com.farnell.client.config.CustomStompSessionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Service
public class WebSocketService {
    private static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
    private static final String TOPIC_DESTINATION = "/topic/public";
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    private final MappingJackson2MessageConverter messageConverter;

    private StompSession session;

    public WebSocketService(MappingJackson2MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @PostConstruct
    public void connect() throws ExecutionException, InterruptedException {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(messageConverter);

        StompSessionHandler sessionHandler = new CustomStompSessionHandler(this);
        session = stompClient.connect(WEBSOCKET_URI, sessionHandler).get();

        session.subscribe(TOPIC_DESTINATION, sessionHandler);
    }

//    private EventDto getSampleEvent(String eventId) {
//        EventDto eventDto = new EventDto();
//        eventDto.setEventId(eventId);
//        eventDto.setEventType(EventDto.EventType.INFO);
//        eventDto.setTimestamp(LocalDateTime.now());
//        eventDto.setDescription("Hello from client");
//        return eventDto;
//    }

    public <T> void sendObject(T object) throws JsonProcessingException {
        log.info("Sending message: {}", object.toString());
        session.send("/app/send-event", object);
    }

    public void retryConnection() {
        try {
            Thread.sleep(5000); // Wait before retrying
            connect();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
