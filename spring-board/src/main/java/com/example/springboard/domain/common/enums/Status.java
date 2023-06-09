package com.example.springboard.domain.common.enums;

import lombok.Getter;

@Getter
public enum Status {
    USING("U"),
    DELETED("D"),
    BLOCKED("B"),
    SLEEP("S");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
