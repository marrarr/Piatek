package com.ZamianaRadianow.security.dto;

public class AuthenticationResponse {

    private String token;
    private Long id;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse(String token, Long id) {
        this.token = token;
        this.id = id;
    }


    // getter
    public String getToken() {
        return token;
    }
}