package com.gabriel.emplms.transform;

import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.ProductData;
import com.gabriel.emplms.model.Product;

@Service
public class TransformProductServiceImpl implements TransformProductService {

    @Override
    public ProductData transform(Product product) {
        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setTotalPrice(product.getTotalPrice());
        productData.setStock(product.getStock());
        productData.setQuantity(product.getQuantity());
        productData.setCartData(product.getCartData());
        return productData;
    }

    @Override
    public Product transform(ProductData productData) {
        Product product = new Product();
        product.setId(productData.getId());
        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setTotalPrice(productData.getTotalPrice());
        product.setStock(productData.getStock());
        product.setQuantity(productData.getQuantity());
        product.setCartData(productData.getCartData());
        return product;
    }
}