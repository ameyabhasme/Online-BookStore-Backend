package com.bridgelabz.bookstore.controller;


import java.util.List;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.service.IBookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping(value = {"","/"})
    public  List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable("bookId") Long bookId) {
        return new ResponseEntity<ResponseDTO>(new ResponseDTO("Get call success",
                bookService.getBookById(bookId)), HttpStatus.OK);
    }

    @GetMapping(value = "bookname/{name}")
    public List<Book> searchByBookName(@PathVariable("name") String name) throws Exception {
        return bookService.getByBookName(name);
    }

    @GetMapping(value = "/sortByName")
    public List<Book> sortByName() {
        return   bookService.sortByName();
    }
    @GetMapping(value = "/sortByPriceHighToLow")
    public List<Book> sortHighToLow() {
        return   bookService.sortByPriceHighToLOw();
    }
    @GetMapping(value = "/sortByPriceLowToHigh")
    public List<Book> sortLowToHigh() {
        return   bookService.sortByPriceLowToHigh();
    }

    @PostMapping("/add")
    ResponseEntity<ResponseDTO> addBook(@RequestBody BookDTO bookDTO){
        return new ResponseEntity<ResponseDTO>(new ResponseDTO("Book added Successfully!!!",
                bookService.addBook(bookDTO)), HttpStatus.OK);
    }


    @GetMapping("/page/{offset}/{size}")
    public ResponseEntity<ResponseDTO> getBookByPage(@PathVariable("offset") int offset , @PathVariable("size") int size){
        Page<Book> bookByPaginate = bookService.getBookByPaginate(offset, size);
        ResponseDTO responseDTO = new ResponseDTO("get call success" , bookByPaginate);
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }
}
