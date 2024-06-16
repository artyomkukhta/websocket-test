package com.farnell.client.controller;

import com.farnell.client.controller.EventDtoConverter;
import com.farnell.client.controller.MqttGateway;
import com.farnell.client.model.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @Autowired
    private EventDtoConverter eventDtoConverter;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message, @RequestParam(required = false) String topic) {
        if (topic != null && !topic.isEmpty()) {
            mqttGateway.sendToMqtt(message, topic, 2); // Установка QoS 2 для отправляемого сообщения
        } else {
            mqttGateway.sendToMqtt(message);
        }
        return "Message sent to MQTT: " + message;
    }

    @PostMapping("/sendEvent")
    public String sendEvent(@RequestBody EventDto eventDto, @RequestParam(required = false) String topic) {
        try {
            String payload = eventDtoConverter.convertToJson(eventDto);
            if (topic != null && !topic.isEmpty()) {
                mqttGateway.sendToMqtt(payload, topic, 2); // Установка QoS 2 для отправляемого сообщения
            } else {
                mqttGateway.sendToMqtt(payload);
            }
            return "Event sent to MQTT: " + eventDto.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Failed to send event to MQTT: " + e.getMessage();
        }
    }
}
