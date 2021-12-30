package com.bridgelabz.bookstore.service.implementation;

import javax.mail.internet.MimeMessage;
import com.bridgelabz.bookstore.config.JwtUtils;
import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public User getUserByEmail(String userName) {
        return userRepository.findUserByEmail(userName);
    }

    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public void sendMail(String recipient, String subject, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        String messageBody = "<b>Hello " + recipient + ",</b><br>";
        messageBody += "<a href=" + message + ">CLICK HERE</a>";
        messageBody += "<p>to reset your password</p>";
        try {
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(messageBody, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        String email = jwtUtils.extractUsername(token);
        UserDetails userDetails = userRepository.findUserByEmail(email);
        if (jwtUtils.validateToken(token, userDetails)) {
            User user = userRepository.findUserByEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Password updated successfully";
        } else {
            return "failed";
        }
    }

}
