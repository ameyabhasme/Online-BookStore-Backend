package com.bridgelabz.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
public @Data class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int wishListId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne
    @JoinColumn(name =  "userId")
    public User userId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne
    @JoinColumn(name =  "bookId")
    public Book bookId;
}
