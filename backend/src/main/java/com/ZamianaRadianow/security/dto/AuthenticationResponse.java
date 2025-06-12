package com.ZamianaRadianow.security.dto;

public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    // getter
    public String getToken() {
        return token;
    }
}