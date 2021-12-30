package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface WishListRepository extends JpaRepository<WishList , Integer> {

    public List<WishList> findAllByUserId(User user);
    public void deleteByBookIdAndUserId(Book book , User user);
    public void deleteByWishListId(int id);

}
