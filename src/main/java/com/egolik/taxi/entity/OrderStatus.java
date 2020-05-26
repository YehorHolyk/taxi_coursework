package com.egolik.taxi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    ON_HOLD(1), PROCESSING(2), COMPLETED(3), CANCELED(4);

    private final int statusCode;

    public static OrderStatus getStatusByStatusCode(int statusCode){
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.getStatusCode() == statusCode)
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
