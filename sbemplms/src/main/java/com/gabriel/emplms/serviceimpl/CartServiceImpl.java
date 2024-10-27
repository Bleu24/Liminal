package com.gabriel.emplms.serviceimpl;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.entity.ProductData;
import com.gabriel.emplms.repository.CartDataRepository;
import com.gabriel.emplms.repository.ProductDataRepository;
import com.gabriel.emplms.service.CartService;


@Service
public class CartServiceImpl implements CartService{

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private ProductDataRepository productDataRepository;

    @Autowired
    private CartDataRepository cartDataRepository;

    @Transactional
    @Override
    public CartData addToCart(int productId, int quantity) {
        // Fetch the product details from product_data table
        ProductData productData = productDataRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    
        // Check if stock is sufficient
        if (productData.getStock() < quantity) {
            logger.warn("Insufficient stock for product ID: " + productData.getId());
            throw new RuntimeException("Insufficient stock for product ID: " + productData.getId());
        }
    
        // Find the cart item by productId
        CartData cartItem = cartDataRepository.findByProductId(productId);
        
        if (cartItem != null) {
            // Product exists in the cart, update quantity and total price
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getPricePerItem() * cartItem.getQuantity());
        } else {
            // Product does not exist in the cart, create a new cart item
            cartItem = new CartData();
            cartItem.setProductId(productData.getId());
            cartItem.setName(productData.getName());
            cartItem.setDescription(productData.getDescription());
            cartItem.setPricePerItem(productData.getPricePerItem());
            cartItem.setTotalPrice(productData.getPricePerItem() * quantity);
            cartItem.setQuantity(quantity);
        }
    
        // Save the cart item (either new or updated)
        cartDataRepository.save(cartItem);
    
        // Update the product stock in product_data
        productData.setStock(productData.getStock() - quantity);
        productDataRepository.save(productData);
    
        return cartItem;
    }
}
