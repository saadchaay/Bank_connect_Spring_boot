package com.bankconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String cin;
    private String address;
    private String cinImage;
    private String accType;

}
