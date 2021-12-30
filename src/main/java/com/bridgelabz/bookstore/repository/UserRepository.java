package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {

    public User findUserByEmail(String userName);
    
}