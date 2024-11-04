package com.reamillo.emplms.transform;

import com.reamillo.emplms.entity.ProductData;
import com.reamillo.emplms.model.Product;
import org.springframework.stereotype.Service;

@Service
public class TransformProductServiceImpl implements TransformProductService {

    @Override
    public ProductData transform(Product product) {
        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setUsername(product.getUsername());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setPricePerItem(product.getPricePerItem());
        productData.setStock(product.getStock());
        return productData;
    }

    @Override
    public Product transform(ProductData productData) {
        Product product = new Product();
        product.setId(productData.getId());
        product.setUsername(productData.getUsername());
        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setPricePerItem(productData.getPricePerItem());
        product.setStock(productData.getStock());
        return product;
    }
}