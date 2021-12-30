package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class CartDTO {

    public Long bookId;

    public Long quantity;
}