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

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class WebSocketService {
    private static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    private final MappingJackson2MessageConverter messageConverter;
  //  private static final String PUBLIC_TOPIC_DESTINATION = "/topic/public";
   // private static final String PRIVATE_QUEUE_DESTINATION = "/user/queue/private"; // Note the "/user" prefix

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
        try {
            session = stompClient.connect(WEBSOCKET_URI, sessionHandler).get();
            //session.subscribe(PUBLIC_TOPIC_DESTINATION, sessionHandler);
            session.subscribe("/user/topic/specific-user/", sessionHandler);
        } catch (Exception e) {
            e.printStackTrace();
            retryConnection();
        }
    }

    public <T> void sendObject(String destination,T object) throws JsonProcessingException {
        log.info("Sending message: {}", object.toString());
        session.send(destination, object);
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
