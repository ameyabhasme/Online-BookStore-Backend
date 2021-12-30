package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.entities.OrderModel;

import java.util.List;

public interface IOrderService {
    public OrderModel getOrderById(int orderId);

    public OrderModel placeOrder(OrderDTO orderDTO);

    public OrderModel updateOrder(int orderId, OrderDTO orderDTO);

    List<OrderModel> getAllOrders();
}