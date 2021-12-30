package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ChangeBookQuantityDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.Cart;
import com.bridgelabz.bookstore.service.ICartService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO){
        return new ResponseEntity<ResponseDTO>(new ResponseDTO("Post call success", cartService.addCart(cartDTO)), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> findAllCartsByUser(){
        List<Cart> carts = cartService.findAllCarts();
        List<Book> books = carts.stream().map(Cart::getBookId).toList();
        ResponseDTO responseDTO = new ResponseDTO("call success" , carts);
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }

    @DeleteMapping("/remove/{cartId}")
    ResponseEntity<ResponseDTO> removeFromCart(@PathVariable("cartId") int cartId){
        cartService.removeFromCart(cartId);
        return new ResponseEntity<ResponseDTO>(new ResponseDTO("Delete call success",""), HttpStatus.OK);
    }

    @DeleteMapping("/deleteFromCart/{bookId}")
    public ResponseEntity<ResponseDTO> removeFromCartByBookId(@PathVariable("bookId") Long bookId){
        cartService.deleteByBookId(bookId);
        ResponseDTO  responseDTO = new ResponseDTO("Delete call success" , "");
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/changeBookQuantity")
    public ResponseEntity<ResponseDTO> changeBookQuantityInCart(@RequestBody ChangeBookQuantityDTO changeBookQuantityDTO){
        Cart cart = cartService.changeBookQuantityInCart(changeBookQuantityDTO.bookId, changeBookQuantityDTO.quantity);
        ResponseDTO responseDTO = new ResponseDTO("quantity changed" , cart);
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }
}
