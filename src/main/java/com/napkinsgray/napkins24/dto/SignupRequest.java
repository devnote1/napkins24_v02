package com.napkinsgray.napkins24.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class SignupRequest {

    private String username;
    private String password;
    private String email;

}
