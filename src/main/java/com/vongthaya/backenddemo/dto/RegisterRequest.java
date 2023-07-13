package com.vongthaya.backenddemo.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String fullname;

    private String email;

    private String password;

}
