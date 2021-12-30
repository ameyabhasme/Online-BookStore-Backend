package com.bridgelabz.bookstore.dto;

import com.bridgelabz.bookstore.entities.Cart;
import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private String message;
    private List<Cart> data;

    public CartResponse(String message, List<Cart> data) {
        this.message = message;
        this.data = data;
    }
}
