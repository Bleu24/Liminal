package com.gabriel.emplms.service;

import java.util.List;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.model.Cart;
import com.gabriel.emplms.model.Product;


public interface CartService {
    CartData addToCart(Product product) throws Exception;
    CartData removeFromCart(Product product) throws Exception;
    List<Cart> getCartContents() throws Exception;
    void clearCart() throws Exception;
    CartData updateCartQuantity(Product product, int quantity) throws Exception;
}
