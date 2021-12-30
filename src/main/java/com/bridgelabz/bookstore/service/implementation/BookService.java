package com.bridgelabz.bookstore.service.implementation;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    public List<Book> getByBookName(String name) throws Exception {
        List<Book> books;

        books = this.getBooks().stream()
                .filter(bookList -> bookList.getBookName().contains(name) || bookList.getAuthorName().contains(name))
                .toList();
        if (books == null)
            throw new Exception("No results were returned. Please refine your search.");
        else
            return books;
    }

    @Override
    public Page<Book> getBookByPaginate(int offset, int size){
        return bookRepository.findAll(PageRequest.of(offset,size));
    }

    @Override
    public Book addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, BookDTO bookDTO) {
        Book book = this.getBookById(bookId);
        book.updateBook(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = this.getBookById(bookId);
        bookRepository.delete(book);
    }

    @Override
    public List<Book> sortByName() {
        return bookRepository.findAll(Sort.by("bookName").ascending());
    }

    @Override
    public List<Book> sortByPriceHighToLOw() {
        return bookRepository.findAll(Sort.by("price").descending());
    }

    @Override
    public List<Book> sortByPriceLowToHigh() {
        return bookRepository.findAll(Sort.by("price").ascending());
    }

}
