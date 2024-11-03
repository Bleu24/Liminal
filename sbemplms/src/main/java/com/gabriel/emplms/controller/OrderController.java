package com.gabriel.emplms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.emplms.entity.OrderData;
import com.gabriel.emplms.model.Order;
import com.gabriel.emplms.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    // Utility method to get the logged-in username
    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToOrder(@RequestBody Order order) {
        String username = getLoggedInUsername();
        logger.info("Input >> " + order.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            OrderData newOrderItem = orderService.addToOrder(username, order.getProductId(), order.getQuantity());
            logger.info("Added to order >> " + newOrderItem.toString());
            orderService.saveOrder(username);
            response = ResponseEntity.ok(newOrderItem);
        } catch (Exception ex) {
            logger.error("Failed to add item to order: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderData>> getAllOrders() {
        String username = getLoggedInUsername();
        List<OrderData> orders = orderService.getAllOrders(username);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOrderItem(@RequestBody Order order) {
        String username = getLoggedInUsername();
        logger.info("Input >> " + order.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
    
        try {
            OrderData updatedItem = orderService.updateOrderItem(username, order.getProductId(), order.getQuantity());
            logger.info("Updated order item >> " + updatedItem.toString());
            response = ResponseEntity.ok(updatedItem);
        } catch (Exception ex) {
            logger.error("Failed to update order item: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    
        return response;
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeOrderItem(@PathVariable int id) {
        String username = getLoggedInUsername();
        orderService.removeOrderItem(username, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveOrder() {
        String username = getLoggedInUsername();
        orderService.saveOrder(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
