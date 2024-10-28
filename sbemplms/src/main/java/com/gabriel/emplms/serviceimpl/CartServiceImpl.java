package com.gabriel.emplms.serviceimpl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

     // Get all items in the cart
    @Override
    public List<CartData> getAllCartItems() {
        List<CartData> cartItems = new ArrayList<>();
        cartDataRepository.findAll().forEach(cartItems::add);
        return cartItems;
    }

    @Transactional
    @Override
    public CartData updateCartItem(int productId, int newQuantity) {
        // Fetch the product details from the product_data table
        ProductData productData = productDataRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find the existing cart item by productId
        CartData cartItem = cartDataRepository.findByProductId(productId);
        if (cartItem == null) {
            throw new RuntimeException("Cart item not found with Product ID: " + productId);
        }

        // Calculate the quantity difference
        int originalQuantity = cartItem.getQuantity();
        int quantityDifference = newQuantity - originalQuantity;

        // Adjust the stock based on the quantity difference
        if (quantityDifference > 0) {
            // Adding more items to the cart, so decrease the stock
            if (productData.getStock() < quantityDifference) {
                throw new RuntimeException("Insufficient stock for product ID: " + productId);
            }
            productData.setStock(productData.getStock() - quantityDifference);
        } else {
            // Removing items from the cart, so increase the stock
            productData.setStock(productData.getStock() + Math.abs(quantityDifference));
        }

        // Update the cart item quantity and total price
        cartItem.setQuantity(newQuantity);
        cartItem.setTotalPrice(cartItem.getPricePerItem().multiply(BigDecimal.valueOf(newQuantity)));

        // Save the updated cart item and product data
        cartDataRepository.save(cartItem);
        productDataRepository.save(productData);

        return cartItem;
    }


      // Remove an item from the cart
      @Override
      @Transactional
      public void removeCartItem(int id) {
          CartData cartItem = cartDataRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + id));
      
          // Find the associated product
          ProductData productData = productDataRepository.findById(cartItem.getProductId())
                  .orElseThrow(() -> new RuntimeException("Product not found for product ID: " + cartItem.getProductId()));
      
          // Restore the stock
          int quantityToRestore = cartItem.getQuantity();
          productData.setStock(productData.getStock() + quantityToRestore);
      
          // Save the updated product data
          productDataRepository.save(productData);
      
          // Remove the item from the cart
          cartDataRepository.deleteById(id);
      }
      

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
            cartItem.setTotalPrice(cartItem.getPricePerItem().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        } else {
            // Product does not exist in the cart, create a new cart item
            cartItem = new CartData();
            cartItem.setProductId(productData.getId());
            cartItem.setName(productData.getName());
            cartItem.setDescription(productData.getDescription());
            cartItem.setPricePerItem(productData.getPricePerItem());
            cartItem.setTotalPrice(productData.getPricePerItem().multiply(BigDecimal.valueOf(quantity)));
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


