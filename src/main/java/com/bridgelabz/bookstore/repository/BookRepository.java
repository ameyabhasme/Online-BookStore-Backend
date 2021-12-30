package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
