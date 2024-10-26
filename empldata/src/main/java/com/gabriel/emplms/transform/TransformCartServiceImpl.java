package com.gabriel.emplms.transform;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.model.Cart;

public class TransformCartServiceImpl implements TransformCartService {

    @Override
    public CartData transform(Cart cart) {
        CartData cartData = new CartData();
        cartData.setId(cart.getId());
        cartData.setName(cart.getName());
        cartData.setProducts(cart.getProducts());
        cartData.setDescription(cart.getDescription());
        cartData.setTotalPrice(cart.getTotalPrice());
        cartData.setQuantity(cart.getQuantity());
        return cartData;
    }

    @Override
    public Cart transform(CartData cartData) {
        Cart cart = new Cart();
        cart.setId(cartData.getId());
        cart.setName(cartData.getName());
        cart.setProducts(cartData.getProducts());
        cart.setDescription(cartData.getDescription());
        cart.setTotalPrice(cartData.getTotalPrice());
        cart.setQuantity(cartData.getQuantity());
        return cart;
    }

}
