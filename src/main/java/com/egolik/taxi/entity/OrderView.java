package com.egolik.taxi.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class OrderView {
    private int orderId;
    private String model;
    private String licensePlate;
    private int userId;
    private String login;
    private String telephoneNumber;
    private String email;
    private String route;
    private Date orderDate;
    private String orderStatus;
}
