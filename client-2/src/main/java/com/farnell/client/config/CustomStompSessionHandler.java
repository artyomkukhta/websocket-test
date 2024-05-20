package com.farnell.client.config;

import com.farnell.client.model.EventDto;
import com.farnell.client.model.SettingsDto;
import com.farnell.client.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
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
        session.subscribe("/topic/events", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return EventDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                EventDto message = (EventDto) payload;
                logger.info("Received: " + message.toString());
            }
        });

        session.subscribe("/user/topic/private-event", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return EventDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                EventDto message = (EventDto) payload;
                logger.info("Received private event: " + message.toString());
            }
        });

        session.subscribe("/user/topic/private-settings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return SettingsDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                SettingsDto message = (SettingsDto) payload;
                logger.info("Received private settings: {}", message.toString());
            }
        });
        session.subscribe("/topic/public-settings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return SettingsDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                SettingsDto message = (SettingsDto) payload;
                logger.info("Received public settings: {}", message.toString());
            }
        });

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
