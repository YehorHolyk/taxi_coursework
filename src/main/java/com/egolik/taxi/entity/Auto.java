package com.egolik.taxi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Auto extends Entity{
    private String brand;
    private String model;
    private String licensePlate;
    private double price;
    private String autoClass;
    private String manufacturer;
    private int seats;
    private String pictureURL;
}
