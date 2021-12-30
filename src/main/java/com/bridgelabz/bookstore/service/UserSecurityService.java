package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user =  userService.getUserByEmail(userName);
        if(user == null){
            System.out.println("No user found by given name");
            throw new UsernameNotFoundException("User not found of given user name");
        }
        return user;
    }
}
