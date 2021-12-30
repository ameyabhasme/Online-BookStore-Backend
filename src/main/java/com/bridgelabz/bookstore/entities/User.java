package com.bridgelabz.bookstore.entities;

import com.bridgelabz.bookstore.dto.UserDTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    private String userName;
    private String phoneNumber;
    private String email;
    private String password;

    public User() {
    }

    public User(UserDTO userDTO) {
        userName = userDTO.userName;
        phoneNumber = userDTO.phoneNumber;
        email = userDTO.email;
        password = userDTO.password;
    }

    @CreationTimestamp
    private LocalDate registerDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set = new HashSet<>();
        set.add(new Authority("user"));
        return set;
    }
}