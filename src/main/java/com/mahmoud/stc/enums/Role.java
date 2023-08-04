package com.mahmoud.stc.enums;

import lombok.Getter;

import java.util.stream.Stream;

public enum Role {

    VIEW("view"),
    EDIT("edit"),
    ADMIN("admin");

@Getter
private final String value;

    Role(String value) {
        this.value = value;
    }



    public static Role fromString(String text) {
        return Stream.of(Role.values())
                .filter( role -> role.value.equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Role Enum exists with value: " + text));

    }
}
