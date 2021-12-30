package com.bridgelabz.bookstore.service.implementation;

import com.bridgelabz.bookstore.config.JwtAuthenticationFilter;
import com.bridgelabz.bookstore.config.JwtUtils;
import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.Cart;
import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public Cart addCart(CartDTO cartDTO) {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User user = userService.getUserByEmail(username);
        if(user.isEnabled()) {
            Book book = bookService.getBookById(cartDTO.bookId);
            Cart cart = new Cart(user, book, cartDTO.quantity);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public void removeFromCart(int cartId) {
        System.out.println("deleted");
        cartRepository.deleteById(cartId);
    }

    @Override
    public void removeAllCarts() {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User userByEmail = userService.getUserByEmail(username);
        cartRepository.deleteAllByUserId(userByEmail);
        System.out.println("all cart items are deleted");
    }

    @Override
    public void updateQuantity(Long bookId, Long quantity) {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User userByEmail = userService.getUserByEmail(username);
        if(userByEmail.isEnabled()){
            Book bookById = bookService.getBookById(bookId);
            Cart byBookId = cartRepository.findByBookId(bookById);
            bookById.setQuantity(quantity);
            cartRepository.save(byBookId);
        }
    }

    @Override
    public List<Cart> findAllCarts() {

        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User user = userService.getUserByEmail(username);

        List<Cart> carts = cartRepository.findAllByUserId(user).stream().filter(item -> !item.isPurchased).toList();
        return carts;
    }

    @Override
    public void deleteByBookId(Long bookId) {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User user = userService.getUserByEmail(username);
        Book bookById = bookService.getBookById(bookId);
        cartRepository.deleteByUserIdAndBookId(user,bookById);
    }

    @Override
    public Cart changeBookQuantityInCart(Long bookId , Long quantity ) {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User user = userService.getUserByEmail(username);
        Book bookById = bookService.getBookById(bookId);
        Cart cart = cartRepository.findByUserIdAndBookId(user, bookById);
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

}