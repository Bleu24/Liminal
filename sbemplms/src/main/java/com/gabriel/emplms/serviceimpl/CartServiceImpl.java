package com.gabriel.emplms.serviceimpl;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.entity.ProductData;
import com.gabriel.emplms.model.Cart;
import com.gabriel.emplms.model.Product;
import com.gabriel.emplms.service.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final List<ProductData> cartItems = new ArrayList<>(); // In-memory cart list using ProductData

    @Override
    public CartData addToCart(Product product) throws Exception {
        logger.info("Adding product to cart: {}", product);

        for (ProductData item : cartItems) {
            if (item.getId() == product.getId()) {
                logger.info("Product already in cart, consider updating quantity.");
                return getCartData(); 
            }
        }

        // Convert Product model to ProductData entity
        ProductData productData = convertToProductData(product);
        cartItems.add(productData); // Add ProductData to the cart
        logger.info("Product added to cart: {}", product);

        return getCartData();
    }

    @Override
    public CartData removeFromCart(Product product) throws Exception {
        logger.info("Removing product from cart: {}", product);

        if (cartItems.removeIf(item -> item.getId() == product.getId())) {
            logger.info("Product removed from cart: {}", product);
        } else {
            logger.warn("Product not found in cart: {}", product);
        }

        return getCartData();
    }

    @Override
    public List<Cart> getCartContents() throws Exception {
        List<Cart> cartContentList = new ArrayList<>();

        for (ProductData item : cartItems) {
            Cart dto = new Cart();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setTotalPrice(item.getTotalPrice()); // Assuming price is set in ProductData
            dto.setDescription(item.getDescription());
            dto.setQuantity(item.getQuantity()); // Set quantity from ProductData
            cartContentList.add(dto);
        }

        return cartContentList;
    }

    @Override
    public void clearCart() throws Exception {
        logger.info("Clearing all items from the cart.");
        cartItems.clear();
    }

    @Override
    public CartData updateCartQuantity(Product product, int quantity) throws Exception {
        logger.info("Updating product quantity: {} to {}", product, quantity);

        for (ProductData item : cartItems) {
            if (item.getId() == product.getId()) {
                item.setQuantity(quantity); // Update quantity in ProductData
                return getCartData();
            }
        }

        throw new Exception("Product not found in cart to update quantity.");
    }

    private CartData getCartData() {
        CartData cartData = new CartData();
        cartData.setProducts(new ArrayList<>(cartItems)); // Set products with ProductData
        cartData.setTotalPrice(calculateTotalPrice());
        return cartData;
    }

    private double calculateTotalPrice() {
        return cartItems.stream()
                .mapToDouble(ProductData::getTotalPrice) // Use ProductData price
                .sum();
    }

    // Utility method to convert Product to ProductData
    private ProductData convertToProductData(Product product) {
        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setTotalPrice(product.getTotalPrice());
        productData.setQuantity(1); // Default quantity, or handle as needed
        return productData;
    }
}
