package com.egolik.taxi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode
@Data
public class Order extends Entity {
    private double sum;
    private Date orderDate;
    private OrderStatus status;
    private int userId;
}
