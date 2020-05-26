package com.egolik.taxi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN(2), USER(1);

    private final int statusCode;

    public static UserRole getRoleByStatusCode(int statusCode) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.getStatusCode() == statusCode)
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
