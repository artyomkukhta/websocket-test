package com.farnell.client.config;//package com.farnell.client.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.converter.MessageConversionException;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.util.MimeTypeUtils;
//
//public class JsonPahoMessageConverter extends DefaultPahoMessageConverter {
//
//    private final ObjectMapper objectMapper;
//
//    public JsonPahoMessageConverter() {
//        this.objectMapper = new ObjectMapper();
//    }
//
//
//    protected MqttMessage createMqttMessage(Object payload, MessageHeaders headers) {
//        try {
//            byte[] payloadBytes = objectMapper.writeValueAsBytes(payload);
//            MqttMessage mqttMessage = new MqttMessage(payloadBytes);
//            Integer qos = (Integer) headers.get("mqtt_qos");
//            if (qos != null) {
//                mqttMessage.setQos(qos);
//            }
//            Boolean retained = (Boolean) headers.get("mqtt_retained");
//            if (retained != null) {
//                mqttMessage.setRetained(retained);
//            }
//            return mqttMessage;
//        } catch (Exception e) {
//            throw new MessageConversionException("Failed to convert message payload to JSON", e);
//        }
//    }
//
//
//    protected Message<?> createMessage(Object payload, MessageHeaders headers) {
//        try {
//            String jsonPayload = new String((byte[]) payload);
//            Object payloadObject = objectMapper.readValue(jsonPayload, Object.class);
//            return MessageBuilder.withPayload(payloadObject)
//                    .copyHeaders(headers)
//                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//                    .build();
//        } catch (Exception e) {
//            throw new MessageConversionException("Failed to convert JSON payload to message", e);
//        }
//    }
//
//    @Override
//    public MqttMessage toMessage(Object payload, MessageHeaders headers) {
//        return createMqttMessage(payload, headers);
//    }
//
//    @Override
//    public Message<?> fromMessage(MqttMessage mqttMessage, MessageHeaders headers) {
//        return createMessage(mqttMessage.getPayload(), headers);
//    }
//}
