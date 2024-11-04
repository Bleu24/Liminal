package com.reamillo.emplms.controller;

import java.util.List;

import com.reamillo.emplms.entity.CartData;
import com.reamillo.emplms.model.Cart;
import com.reamillo.emplms.service.CartService;
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


@RestController
@RequestMapping("/api/cart")
public class CartController {

    Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

     // Utility method to get the logged-in username
    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Gets the username from the security context
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {
        String username = getLoggedInUsername();
        logger.info("Input >> " + cart.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            CartData newCartItem = cartService.addToCart(username, cart.getProductId(), cart.getQuantity());
            logger.info("Added to cart >> " + newCartItem.toString());
            cartService.saveCartItems(username);
            response = ResponseEntity.ok(newCartItem);
        } catch (Exception ex) {
            logger.error("Failed to add item to cart: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        
        return response;
    }

    // Retrieve all items in the cart
    @GetMapping("/items")
    public ResponseEntity<List<CartData>> getCartItems() {
        String username = getLoggedInUsername();
        List<CartData> cartItems = cartService.getAllCartItems(username);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody Cart cart) {
        String username = getLoggedInUsername();
        logger.info("Input >> " + cart.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
    
        try {
            CartData updatedItem = cartService.updateCartItem(username, cart.getProductId(), cart.getQuantity());
            logger.info("Updated cart item >> " + updatedItem.toString());
            response = ResponseEntity.ok(updatedItem);
        } catch (Exception ex) {
            logger.error("Failed to update cart item: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    
        return response;
    }

    // Remove a specific item from the cart
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable int id) {
        String username = getLoggedInUsername();
        cartService.removeCartItem(username, id);
        return ResponseEntity.noContent().build();
    }

}