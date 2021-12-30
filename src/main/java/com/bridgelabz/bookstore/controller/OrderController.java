package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.entities.OrderModel;
import com.bridgelabz.bookstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/order")
public class OrderController {


    @Autowired
    IOrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestBody OrderDTO orderDTO){
        System.out.println("The order dto is " + orderDTO);
        OrderModel order = orderService.placeOrder(orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("order place successfully" ,order);
        return  new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<ResponseDTO> getAllOrders(){
        List<OrderModel> allOrders = orderService.getAllOrders();
        ResponseDTO responseDTO = new ResponseDTO("all orders are here" , allOrders);
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }
}