package com.gabriel.emplms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.model.Cart;
import com.gabriel.emplms.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {
        logger.info("Input >> " + cart.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            CartData newCartItem = cartService.addToCart(cart.getProductId(), cart.getQuantity());
            logger.info("Added to cart >> " + newCartItem.toString());
            response = ResponseEntity.ok(newCartItem);
        } catch (Exception ex) {
            logger.error("Failed to add item to cart: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        
        return response;
    }
}