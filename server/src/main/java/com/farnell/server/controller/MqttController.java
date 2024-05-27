package com.farnell.server.controller;


import com.farnell.server.controller.dto.EventDto;
import com.farnell.server.controller.dto.SettingsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @Autowired
    private DtoConverter dtoConverter;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message, @RequestParam(required = false) String topic) {
        if (topic != null && !topic.isEmpty()) {
            mqttGateway.sendToMqtt(message, topic, 2); // Установка QoS 2 для отправляемого сообщения
        } else {
            mqttGateway.sendToMqtt(message);
        }
        return "Message sent to MQTT: " + message;
    }

    @PostMapping("/sendSettings")
    public String sendSettings(@RequestBody SettingsDto settingsDto, @RequestParam(required = false) String topic) {
        try {
            String payload = dtoConverter.convertToJson(settingsDto);
            if (topic != null && !topic.isEmpty()) {
                mqttGateway.sendToMqtt(payload, topic, 2); // Установка QoS 2 для отправляемого сообщения
            } else {
                mqttGateway.sendToMqtt(payload);
            }
            return "Settings sent to MQTT: " + settingsDto.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Failed to send settings to MQTT: " + e.getMessage();
        }
    }
}
