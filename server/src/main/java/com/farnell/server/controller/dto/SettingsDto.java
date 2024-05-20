package com.farnell.server.controller.dto;

import lombok.Data;

@Data
public class SettingsDto {
    private String name;
    private String videoFormat;
    private boolean online;
}
