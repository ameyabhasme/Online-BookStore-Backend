package com.bridgelabz.bookstore.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
public class Book implements Serializable{
	// private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bookId;
	private Long userId;
	private String bookName;
	private Long quantity;
	private Double price;
	private String authorName;
	@Column(columnDefinition="TEXT")
	private String description;
	private LocalDateTime createdDateAndTime;
	private LocalDateTime updatedDateAndTime;
	private String status;
	private String image;



	public Book() {
	}

	public Book(BookDTO bookDTO) {
		this.updateBook(bookDTO);

	}

	public void updateBook(BookDTO bookDTO) {
		this.bookName = bookDTO.bookName;
		this.authorName = bookDTO.authorName;
		this.description = bookDTO.description;
		this.image = bookDTO.image;
		this.price = bookDTO.price;
		this.quantity = bookDTO.quantity;

	}
}