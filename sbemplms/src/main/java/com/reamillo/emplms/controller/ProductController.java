package com.reamillo.emplms.controller;

import com.reamillo.emplms.model.Product;
import com.reamillo.emplms.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private ProductService productService;

    @GetMapping("/api/product")
    public ResponseEntity<?> listProduct() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product[] products = productService.getAll();
            response = ResponseEntity.ok().headers(headers).body(products);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("/api/product")
    public ResponseEntity<?> add(@RequestBody Product product) {
        logger.info("Input >> " + product.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product newProduct = productService.create(product);
            logger.info("Created customer >> " + newProduct.toString());
            response = ResponseEntity.ok(newProduct);
        } catch (Exception ex) {
            logger.error("Failed to create customer: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("/api/product")
    public ResponseEntity<?> update(@RequestBody Product product) {
        logger.info("Update Input >> " + product.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product updatedProduct = productService.update(product);
            response = ResponseEntity.ok(updatedProduct);
        } catch (Exception ex) {
            logger.error("Failed to update customer: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        logger.info("Input customer id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Product product = productService.get(id);
            response = ResponseEntity.ok(product);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        logger.info("Input >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            productService.delete(id);
            response = ResponseEntity.ok(null);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}