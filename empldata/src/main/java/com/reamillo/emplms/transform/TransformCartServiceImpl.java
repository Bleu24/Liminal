package com.reamillo.emplms.transform;

import com.reamillo.emplms.entity.CartData;
import com.reamillo.emplms.model.Cart;

public class TransformCartServiceImpl implements TransformCartService {

    @Override
    public CartData transform(Cart cart) {
        CartData cartData = new CartData();
        cartData.setId(cart.getId());
        cartData.setUsername(cart.getUsername());
        cartData.setName(cart.getName());
        cartData.setProductId(cart.getProductId());
        cartData.setDescription(cart.getDescription());
        cartData.setTotalPrice(cart.getTotalPrice());
        cartData.setPricePerItem(cart.getPricePerItem());
        cartData.setQuantity(cart.getQuantity());
        return cartData;
    }

    @Override
    public Cart transform(CartData cartData) {
        Cart cart = new Cart();
        cart.setId(cartData.getId());
        cart.setUsername(cartData.getUsername());
        cart.setName(cartData.getName());
        cart.setProductId(cartData.getProductId());
        cart.setDescription(cartData.getDescription());
        cart.setTotalPrice(cartData.getTotalPrice());
        cart.setQuantity(cartData.getQuantity());
        return cart;
    }

}
