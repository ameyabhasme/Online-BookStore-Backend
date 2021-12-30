package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.entities.OrderModel;
import com.bridgelabz.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    public List<OrderModel> findAll();
    public List<OrderModel> findAllByUserId(User user);
    public OrderModel findById(int id);
}
