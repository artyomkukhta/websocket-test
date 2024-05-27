package com.farnell.server.controller;

import com.farnell.server.controller.dto.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    private final ObjectMapper objectMapper;

    public DtoConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convertToJson(Object eventDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(eventDto);
    }
}
