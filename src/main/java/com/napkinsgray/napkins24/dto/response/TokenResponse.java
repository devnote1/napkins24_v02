package com.napkinsgray.napkins24.dto.response;

import lombok.Data;

@Data
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
