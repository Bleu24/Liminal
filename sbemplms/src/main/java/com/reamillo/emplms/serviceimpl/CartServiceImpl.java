package com.reamillo.emplms.serviceimpl;


import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.reamillo.emplms.entity.CartData;
import com.reamillo.emplms.entity.ProductData;
import com.reamillo.emplms.repository.CartDataRepository;
import com.reamillo.emplms.repository.ProductDataRepository;
import com.reamillo.emplms.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private ProductDataRepository productDataRepository;

    @Autowired
    private CartDataRepository cartDataRepository;

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Gets the username from the security context
    }

     // Get all items in the cart
    @Override
    public List<CartData> getAllCartItems(String username) {
        return cartDataRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public CartData updateCartItem(String username, int productId, int newQuantity) {
        // Fetch the product details from the product_data table
        ProductData productData = productDataRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find the existing cart item by productId
        CartData cartItem = cartDataRepository.findByUsernameAndProductId(username, productId);
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
      public void removeCartItem(String username, int id) {
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
    public CartData addToCart(String username, int productId, int quantity) {
        // Fetch the product details from product_data table
        ProductData productData = productDataRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    
        // Check if stock is sufficient
        if (productData.getStock() < quantity) {
            logger.warn("Insufficient stock for product ID: " + productData.getId());
            throw new RuntimeException("Insufficient stock for product ID: " + productData.getId());
        }
    
        // Find the cart item by productId
        CartData cartItem = cartDataRepository.findByUsernameAndProductId(username, productId);
        
        if (cartItem != null) {
            // Product exists in the cart, update quantity and total price
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getPricePerItem().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        } else {
            // Product does not exist in the cart, create a new cart item
            cartItem = new CartData();
            cartItem.setProductId(productData.getId());
            cartItem.setUsername(getLoggedInUsername());
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

    @Override
    public void saveCartItems(String username) {
        // Fetch all cart items for the user from the database
        List<CartData> cartItems = cartDataRepository.findByUsername(username);

        // Save all cart items for the user
        cartDataRepository.saveAll(cartItems);
    }
}


