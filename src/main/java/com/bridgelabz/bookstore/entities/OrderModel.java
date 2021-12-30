package com.bridgelabz.bookstore.entities;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "OrderModel")
public @Data class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String name;

    public String phoneNo;

    public String city;

    public String landmark;

    public String addressType;

    @CreationTimestamp
    public LocalDateTime orderDate;

    public Long quantity;

    public Float price;

    public String address;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToMany
    private List<Book> bookId;

    public OrderModel(){}


    public OrderModel(OrderDTO orderDTO) {
        this.name = orderDTO.name;
        this.phoneNo = orderDTO.phoneNo;
        this.city = orderDTO.city;
        this.landmark = orderDTO.landmark;
        this.addressType = orderDTO.addressType;
        this.address = orderDTO.address;
    }
}
