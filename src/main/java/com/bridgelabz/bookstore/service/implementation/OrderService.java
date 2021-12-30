package com.bridgelabz.bookstore.service.implementation;

import com.bridgelabz.bookstore.config.JwtAuthenticationFilter;
import com.bridgelabz.bookstore.config.JwtUtils;
import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.Cart;
import com.bridgelabz.bookstore.entities.OrderModel;
import com.bridgelabz.bookstore.entities.User;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartService cartService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    UserService userService;

    @Override
    public OrderModel getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public OrderModel placeOrder(OrderDTO orderDTO) {
        List<Book> books = new ArrayList<>();

        List<Cart> cartItems = cartService.findAllCarts();

        books = cartItems.stream().map(Cart::getBookId).toList();

        System.out.println("this is order DTO => "+orderDTO);

        float totalPrice = (float) cartItems.stream().map(singleItem -> singleItem.getBookId().getPrice() * singleItem.getQuantity()).toList().stream().mapToDouble(eachItemPrice -> eachItemPrice).sum();

        long quantity = cartItems.size();

        System.out.println(totalPrice+" "+ quantity+ " "+ books);
        OrderModel order = new OrderModel(orderDTO);
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User user = userService.getUserByEmail(username);
        order.setUserId(user);
        order.setPrice(totalPrice);
        order.setQuantity(quantity);
        order.setBookId(books);

        cartItems.forEach(cart -> {
//            cart.isPurchased = true;
//            CartDTO cartDTO = new CartDTO();
//            cartDTO.bookId = cart.getBookId().getBookId();
//            cartDTO.quantity = cart.getQuantity();
            cartService.removeFromCart(cart.getCartId());
//            cartService.addCart(cartDTO);

        });

        return  orderRepository.save(order);

    }

    @Override
    public OrderModel updateOrder(int orderId, OrderDTO orderDTO) {
        OrderModel byId = orderRepository.findById(orderId);
        byId.name = orderDTO.name;
        byId.phoneNo = orderDTO.phoneNo;
        byId.city = orderDTO.city;
        byId.landmark = orderDTO.landmark;
        byId.addressType = orderDTO.addressType;
        byId.address = orderDTO.address;
        return orderRepository.save(byId);
    }

    @Override
    public List<OrderModel> getAllOrders() {
        String username = jwtUtils.extractUsername(jwtAuthenticationFilter.jwtToken);
        User user = userService.getUserByEmail(username);
        return orderRepository.findAllByUserId(user);
    }

}