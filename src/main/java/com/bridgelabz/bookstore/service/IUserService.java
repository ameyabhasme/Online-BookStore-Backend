package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.entities.User;

public interface IUserService {

    public User getUserByEmail(String userName);

    public User addUser(UserDTO userDTO);

    public void sendMail(String recipient, String subject, String message);

    public String resetPassword(String token, String newPassword);
}
