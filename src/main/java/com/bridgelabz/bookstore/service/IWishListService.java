package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.entities.WishList;

import java.util.List;

public interface IWishListService {
    public List<WishList> getAllWishLists();

    public WishList addToWishList(Long bookId);

    public void deleteFromWishList(int wishListId);
    public void deleteFromWishListByBookId(Long bookId);

}
