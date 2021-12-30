package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class ChangeBookQuantityDTO {
    public Long bookId;
    public Long quantity;
}
