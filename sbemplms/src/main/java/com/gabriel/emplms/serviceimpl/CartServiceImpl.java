package com.gabriel.emplms.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.ProductData;
import com.gabriel.emplms.repository.CartDataRepository;
import com.gabriel.emplms.repository.ProductDataRepository;

import javax.transaction.Transactional;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.service.CartService;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private ProductDataRepository productDataRepository;

    @Autowired
    private CartDataRepository cartDataRepository;

    @Transactional
    @Override
    public String addToCart(int productId, int quantity) {
        // Fetch the product details from product_data table
        ProductData productData = productDataRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if stock is sufficient
        if (productData.getStock() < quantity) {
            return "Insufficient stock";
        }

        // Create a new Cart item and save it to the cart_data table
        CartData cartItem = new CartData();
        cartItem.setProductId(productData.getId());
        cartItem.setName(productData.getName());
        cartItem.setTotalPrice(productData.getTotalPrice());
        cartItem.setQuantity(quantity);
        cartDataRepository.save(cartItem);

        // Update the product stock in product_data
        productData.setStock(productData.getStock() - quantity);
        productDataRepository.save(productData);

        return "Product added to cart successfully";
    }
}
