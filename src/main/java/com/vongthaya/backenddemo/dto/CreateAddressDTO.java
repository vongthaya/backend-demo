package com.vongthaya.backenddemo.dto;

import lombok.Data;

@Data
public class CreateAddressDTO {

    private String line1;

    private String line2;

    private String zipcode;

    private int userId;

}
