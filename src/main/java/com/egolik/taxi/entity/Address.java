package com.egolik.taxi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends Entity {
    private String district;
    private String street;
    private String homeNumber;
}
