package com.bridgelabz.bookstore.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class BookDTO {

	public String bookName;
	public Long quantity;
	public Double price;
	public String authorName;
	public String image;
	public String description;
	public String rating;
	public String userId;

}