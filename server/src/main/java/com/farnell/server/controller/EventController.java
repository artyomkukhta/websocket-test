package com.farnell.server.controller;

import com.farnell.server.controller.dto.SettingsDto;
import com.farnell.server.service.SettingsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;

@RestController("/settings")
@RequestMapping("/settings")
public class EventController {

    private static final Logger log = LogManager.getLogger(EventController.class);
    private final SettingsService service;

    public EventController(SettingsService service) {
        this.service = service;
    }

    @PostMapping("/send-all")
    public void sendMessage(@RequestBody final SettingsDto settingsDto) {
        service.sendAll(settingsDto);
        log.info("Settings {} sent to all clients", settingsDto);
        //return ResponseEntity.ok().build();
    }

    @PostMapping("/send-specific/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
                                   @RequestBody final SettingsDto settingsDto) {
        service.send(id, settingsDto);
        log.info("Settings {} sent to client {}", settingsDto, id);

      //  return ResponseEntity.ok().build(); так вылетает ошибка с GET
    }
}
