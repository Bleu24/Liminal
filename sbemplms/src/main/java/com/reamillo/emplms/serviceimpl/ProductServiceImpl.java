package com.reamillo.emplms.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.reamillo.emplms.entity.ProductData;
import com.reamillo.emplms.model.Product;
import com.reamillo.emplms.service.ProductService;
import com.reamillo.emplms.transform.TransformProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reamillo.emplms.repository.ProductDataRepository;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    ProductDataRepository productDataRepository;

    @Autowired
    TransformProductService transformerProductService;


    @Override
    public Product[] getAll() {
        List<ProductData> productsData = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        productDataRepository.findAll().forEach(productsData::add);
        Iterator<ProductData> it = productsData.iterator();
        while (it.hasNext()) {
            ProductData productData = it.next();
            Product product = transformerProductService.transform(productData);
            products.add(product);
        }
        Product[] array = new Product[products.size()];
        for (int i = 0; i < products.size(); i++) {
            array[i] = products.get(i);
        }
        return array;
    }

    @Override
    public Product create(Product product) {
        logger.info(" add:Input " + product.toString());
        ProductData productData = transformerProductService.transform(product);
        productData = productDataRepository.save(productData);
        logger.info(" add:Input " + productData.toString());
        Product newProduct = transformerProductService.transform(productData);
        return newProduct;
    }

    @Override
    public Product update(Product product) {
        ProductData productData = transformerProductService.transform(product);
        productData = productDataRepository.save(productData);
        Product newProduct = transformerProductService.transform(productData);
        return newProduct;
    }

    @Override
    public Product get(Integer id) {
        logger.info(" Input id >> " + Integer.toString(id));
        Optional<ProductData> optional = productDataRepository.findById(id);
        if (optional.isPresent()) {
            logger.info(" Is present >> ");
            ProductData productDatum = optional.get();
            Product product = transformerProductService.transform(productDatum);
            return product;
        }
        logger.info(" Failed >> unable to locate id: " + Integer.toString(id));
        return null;
    }

    @Override
    public void delete(Integer id) {
        logger.info(" Input >> " + Integer.toString(id));
        Optional<ProductData> optional = productDataRepository.findById(id);
        if (optional.isPresent()) {
            ProductData productDatum = optional.get();
            productDataRepository.delete(productDatum);
            logger.info(" Success >> " + productDatum.toString());
        } else {
            logger.info(" Failed >> unable to locate customer id:" + Integer.toString(id));
        }
    }

    // @Override
    // public ProductData addToCart(ProductDTO productDTO) {
    //     logger.info("Adding product to cart: " + productDTO.toString());

        
    //     ProductData addedToCartProduct = new ProductData();
    //     addedToCartProduct.setId(productDTO.getId());
    //     addedToCartProduct.setName(productDTO.getName());
    //     addedToCartProduct.setPrice(productDTO.getPrice());
    //     addedToCartProduct.setDescription(productDTO.getDescription());
    //     addedToCartProduct.setQuantity(0); // Set default quantity to 1

    //     // Save the added product to the cart
    //     addedToCartProduct = productDataRepository.save(addedToCartProduct);
    //     logger.info("Product added to cart: " + addedToCartProduct.toString());

    //     // Return the ProductData associated with the added product
    //     return transformerProductService.transform(addedToCartProduct);
    // }
    


}
