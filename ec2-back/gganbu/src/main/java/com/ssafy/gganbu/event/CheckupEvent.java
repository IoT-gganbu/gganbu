package com.ssafy.gganbu.event;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CheckupEvent {
    private String name;

    public CheckupEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }}
