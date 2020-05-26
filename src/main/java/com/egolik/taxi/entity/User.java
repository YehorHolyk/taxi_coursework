package com.egolik.taxi.entity;

import lombok.Data;


@Data
public class User extends Entity{
    private String login;
    private String name;
    private String email;
    private UserRole status;
    private String password;
    private String telephoneNumber;
}
