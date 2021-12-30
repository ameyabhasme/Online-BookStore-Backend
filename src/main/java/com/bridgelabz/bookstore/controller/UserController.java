package com.bridgelabz.bookstore.controller;

import javax.validation.Valid;

import com.bridgelabz.bookstore.config.JwtAuthenticationFilter;
import com.bridgelabz.bookstore.config.JwtUtils;
import com.bridgelabz.bookstore.dto.*;
import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.service.IUserService;
import com.bridgelabz.bookstore.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/addUser")
    public User addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest jwtRequest) throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new Exception("Bad Credentials!");
        }

        UserDetails userDetails1 = this.userSecurityService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails1);
        return ResponseEntity.ok(new JWTResponse(token));
    
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getUser")
    public ResponseEntity<ResponseDTO> getUserByToken(){
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        UserDetails userDetails = this.userSecurityService.loadUserByUsername(username);
        User userByEmail = userService.getUserByEmail(userDetails.getUsername());
        System.out.println();
        ResponseDTO responseDTO = new ResponseDTO("user retrieved" , userByEmail);
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);

    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseDTO> sendForgetPasswordEmail(@RequestBody ForgotPasswordDTO forgotPasswordDTO) throws Exception {
        User userByEmail = userService.getUserByEmail(forgotPasswordDTO.getEmail());
        String token = null;
        ResponseDTO responseDTO = null;
        try{
            if (userByEmail != null) {
                token = jwtUtils.generateToken(userByEmail);
                userService.sendMail(userByEmail.getEmail(), "Reset Password Link", "http://localhost:4200/resetPassword/:" + token);
                responseDTO = new ResponseDTO("mail sent successfully" , forgotPasswordDTO.getEmail());
            }else {
                throw new Exception("User not found with given email id");
            }
        }
        catch (Exception e){
            responseDTO = new ResponseDTO("mail not sent" , forgotPasswordDTO.getEmail()+" ** no user available of given email");

        }
            return new ResponseEntity<>(responseDTO , HttpStatus.OK);

    }
	@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/resetPassword/{token}")
    public ResponseEntity<ResponseDTO> updatePassword(@PathVariable("token") String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        ResponseDTO responseDTO = new ResponseDTO("This is Received token", userService.resetPassword(token, resetPasswordDTO.getNewPassword()));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
