package com.gabriel.emplms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gabriel.emplms.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam int productId, @RequestParam int quantity) {
        String response = cartService.addToCart(productId, quantity);
        return ResponseEntity.ok(response);
    }
}
