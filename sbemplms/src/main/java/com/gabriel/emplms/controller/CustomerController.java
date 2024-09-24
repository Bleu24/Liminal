package com.gabriel.emplms.controller;

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

import com.gabriel.emplms.model.Customer;
import com.gabriel.emplms.service.CustomerService;

@RestController
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customer")
    public ResponseEntity<?> listCustomer() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Customer[] customers = customerService.getAll();
            response = ResponseEntity.ok().headers(headers).body(customers);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("/api/customer")
    public ResponseEntity<?> add(@RequestBody Customer customer) {
        logger.info("Input >> " + customer.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Customer newCustomer = customerService.create(customer);
            logger.info("Created customer >> " + newCustomer.toString());
            response = ResponseEntity.ok(newCustomer);
        } catch (Exception ex) {
            logger.error("Failed to create customer: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("/api/customer")
    public ResponseEntity<?> update(@RequestBody Customer customer) {
        logger.info("Update Input >> " + customer.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Customer updatedCustomer = customerService.update(customer);
            response = ResponseEntity.ok(updatedCustomer);
        } catch (Exception ex) {
            logger.error("Failed to update customer: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("/api/customer/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        logger.info("Input customer id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Customer customer = customerService.get(id);
            response = ResponseEntity.ok(customer);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("/api/customer/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        logger.info("Input >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            customerService.delete(id);
            response = ResponseEntity.ok(null);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}