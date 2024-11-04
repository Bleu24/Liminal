package com.reamillo.emplms.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.reamillo.emplms.entity.CartData;
import com.reamillo.emplms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reamillo.emplms.entity.OrderData;
import com.reamillo.emplms.repository.OrderDataRepository;
import com.reamillo.emplms.repository.CartDataRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDataRepository orderRepository;

    @Autowired
    private CartDataRepository cartDataRepository;

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Gets the username from the security context
    }

    @Transactional
    @Override
    public OrderData addToOrder(String username, int productId, int quantity) {
        // Fetch the product details from product_data table
        CartData cartData = cartDataRepository.findByUsernameAndProductId(username, productId);
    
        // Check if stock is sufficient
        if (cartData.getQuantity() < quantity) {
            logger.warn("Insufficient quanity for product ID: " + cartData.getId());
            throw new RuntimeException("Insufficient stock for product ID: " + cartData.getId());
        }
    
        // Find the cart item by productId
        OrderData orderItem = orderRepository.findByUsernameAndProductId(username, productId);
        
        if (orderItem != null) {
            // Product exists in the cart, update quantity and total price
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            orderItem.setTotalPrice(orderItem.getPricePerItem().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        } else {
            // Product does not exist in the cart, create a new cart item
            orderItem = new OrderData();
            orderItem.setProductId(cartData.getId());
            orderItem.setUsername(getLoggedInUsername());
            orderItem.setName(cartData.getName());
            orderItem.setPricePerItem(cartData.getPricePerItem());
            orderItem.setTotalPrice(cartData.getPricePerItem().multiply(BigDecimal.valueOf(quantity)));
            orderItem.setQuantity(quantity);
        }
    
        // Save the order item (either new or updated)
        orderRepository.save(orderItem);
    
        // Update the product qty in cart
        cartData.setQuantity(0);
        cartDataRepository.save(cartData);
    
        return orderItem;
    }

    @Override
    @Transactional
    public List<OrderData> getAllOrders(String username) {
        return orderRepository.findByUsername(username);
    }

      @Override
      @Transactional
      public void removeOrderItem(String username, int id) {
          OrderData orderItem = orderRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Order item not found with ID: " + id));
      
          // Find the associated product
          CartData cartData = cartDataRepository.findById(orderItem.getProductId())
                  .orElseThrow(() -> new RuntimeException("Cart not found for product ID: " + orderItem.getProductId()));
      
          // Restore the stock
          int quantityToRestore = orderItem.getQuantity();
          cartData.setQuantity(cartData.getQuantity() + quantityToRestore);
      
          // Save the updated product data
          cartDataRepository.save(cartData);
      
          // Remove the item from the cart
          orderRepository.deleteById(id);
      }

    @Override
    public void saveOrder(String username) {
        List<OrderData> orders = orderRepository.findByUsername(username);
        for (OrderData order : orders) {
            if (order.getQuantity() > 0) {
                // Save each order without cascading
                orderRepository.save(order);
            }
        }
    }
}
