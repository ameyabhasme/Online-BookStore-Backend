package com.bridgelabz.bookstore.service.implementation;

import com.bridgelabz.bookstore.config.JwtAuthenticationFilter;
import com.bridgelabz.bookstore.config.JwtUtils;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.entities.WishList;
import com.bridgelabz.bookstore.repository.WishListRepository;
import com.bridgelabz.bookstore.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService implements IWishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Override
    public List<WishList> getAllWishLists() {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User userByEmail = userService.getUserByEmail(username);
        List<WishList> allByUserId = wishListRepository.findAllByUserId(userByEmail);
        return allByUserId;
    }

    @Override
    public WishList addToWishList(Long bookId) {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User userByEmail = userService.getUserByEmail(username);
        Book bookById = bookService.getBookById(bookId);
        WishList wishListItem = new WishList();
        wishListItem.setBookId(bookById);
        wishListItem.setUserId(userByEmail);
        WishList save = wishListRepository.save(wishListItem);
        return save;
    }

    @Override
    public void deleteFromWishList(int wishListId) {
//        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
//        User userByEmail = userService.getUserByEmail(username);
        wishListRepository.deleteByWishListId(wishListId);
    }
    @Override
    public void deleteFromWishListByBookId(Long bookId){
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User userByEmail = userService.getUserByEmail(username);
        Book bookById = bookService.getBookById(bookId);
        wishListRepository.deleteByBookIdAndUserId(bookById,userByEmail);

    }
}
