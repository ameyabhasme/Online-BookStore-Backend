package com.bridgelabz.bookstore.dto;

import lombok.Data;

public @Data class JWTRequest {

    private String email;
    private String password;

    public JWTRequest() {}

    public JWTRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

