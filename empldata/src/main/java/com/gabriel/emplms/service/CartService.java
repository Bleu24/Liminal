package com.gabriel.emplms.service;

import java.util.List;

import com.gabriel.emplms.entity.CartData;

public interface CartService {
     public CartData addToCart(String username, int productId, int quantity);
     List<CartData> getAllCartItems(String username);
     CartData updateCartItem(String username, int productId, int quantity);
     void removeCartItem(String username, int id);
    public void saveCartItems(String username);
}
