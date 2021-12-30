package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.Cart;
import com.bridgelabz.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart , Integer> {

    public List<Cart> findAllByUserId(User user);
    public Cart findByBookId(Book book);
    public void deleteAllByUserId(User user);
    public void deleteByUserIdAndBookId(User user , Book book);
    public Cart findByUserIdAndBookId(User user , Book book);
}