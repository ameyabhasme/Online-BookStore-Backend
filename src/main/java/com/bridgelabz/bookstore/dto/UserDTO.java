package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "{error in user name}")
    public String userName;

    public String phoneNumber;

    @NotNull
    public String email;

    @NotNull
    public String password;

}
