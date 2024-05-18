package com.farnell.client.config;

import com.farnell.client.model.EventDto;
import com.farnell.client.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

public class CustomStompSessionHandler extends StompSessionHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CustomStompSessionHandler.class);
    private final WebSocketService webSocketService;

    public CustomStompSessionHandler(WebSocketService webSocketClientConfig) {
        this.webSocketService = webSocketClientConfig;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("Connected to WebSocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Error in WebSocket session", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Transport error in WebSocket session", exception);
        if (exception instanceof ConnectionLostException) {
            // Retry connection
            webSocketService.retryConnection();
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return EventDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        EventDto eventDto = (EventDto) payload;
        logger.info("Received event: {}", eventDto.toString());
    }
}
