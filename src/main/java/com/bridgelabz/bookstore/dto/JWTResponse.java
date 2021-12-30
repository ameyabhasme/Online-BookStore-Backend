package com.bridgelabz.bookstore.dto;

import lombok.Data;

public @Data class JWTResponse {

    private String token;

    public JWTResponse() {
    }

    public JWTResponse(String token) {
        this.token = token;
    }
}
