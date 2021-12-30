package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.entities.Book;

import org.springframework.data.domain.Page;

public interface IBookService {
   public List<Book> getBooks();

   public Book getBookById(Long bookId);

   public List<Book> getByBookName(String name) throws Exception;

   public Book addBook(BookDTO bookDTO);

   public Book updateBook(Long bookId, BookDTO bookDTO);

   public void deleteBook(Long bookId);

   public List<Book> sortByName();

   public List<Book> sortByPriceHighToLOw();

   public List<Book> sortByPriceLowToHigh();

   public Page<Book> getBookByPaginate(int offset, int size);
}
