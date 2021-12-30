package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.entities.Cart;

import java.util.List;

public interface ICartService {

    Cart addCart(CartDTO carServiceDTO);

    void removeFromCart(int cartId);

    void removeAllCarts();

    void updateQuantity(Long bookId, Long quantity);

    List<Cart> findAllCarts();

    public void deleteByBookId(Long bookId);

    public Cart changeBookQuantityInCart(Long bookId , Long quantity);
}