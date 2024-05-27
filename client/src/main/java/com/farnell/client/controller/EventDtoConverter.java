package com.farnell.client.controller;

import com.farnell.client.model.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EventDtoConverter {

    private final ObjectMapper objectMapper;

    public EventDtoConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convertToJson(EventDto eventDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(eventDto);
    }
}
