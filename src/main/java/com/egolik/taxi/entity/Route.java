package com.egolik.taxi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sun.nio.cs.CharsetMapping;

@EqualsAndHashCode
@Data
public class Route extends CharsetMapping.Entry {
    private int priority;
    private int addressId;
    private int orderId;
}
