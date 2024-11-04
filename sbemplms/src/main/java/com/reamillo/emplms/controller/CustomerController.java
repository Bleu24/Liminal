package com.reamillo.emplms.controller;

import com.reamillo.emplms.entity.CustomerData;
import com.reamillo.emplms.model.Customer;
import com.reamillo.emplms.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private final CustomerService customerService;

    @GetMapping("/api/customer/me")
    public ResponseEntity<CustomerData> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomerData currentUser = (CustomerData) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    
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

    // @PostMapping("/api/register")
    // public ResponseEntity<?> registerCustomer(@RequestBody CustomerSignUpDTO customerSignUpDTO) {
    //     logger.info("Register Input >> " + customerSignUpDTO.toString());
    //     HttpHeaders headers = new HttpHeaders();
    //     ResponseEntity<?> response;
    //     try {
    //         // Call the service to register the customer
    //         CustomerData newCustomer = customerService.register(customerSignUpDTO);
    //         response = ResponseEntity.ok(newCustomer);
    //     } catch (Exception ex) {
    //         logger.error("Failed to register customer: {}", ex.getMessage(), ex);
    //         response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    //     }
    //     return response;
    // }

    // @PostMapping("/api/login")
    // public ResponseEntity<?> loginCustomer(@Valid @RequestBody CustomerLoginDTO customerLoginDTO) {
    //     logger.info("Login Input >> " + customerLoginDTO.toString());
    //     ResponseEntity<?> response;
    //     try {
    //         // Authenticate the customer
    //         CustomerData customer = customerService.authenticate(customerLoginDTO);

    //         // Generate JWT token
    //         String token = jwtTokenProvider.generateToken(customer);

    //         // Return response with token and user details
    //         Map<String, Object> responseBody = new HashMap<>();
    //         responseBody.put("message", "Login successful");
    //         responseBody.put("token", token);
    //         responseBody.put("username", customer.getUsername());

    //         response = ResponseEntity.ok(responseBody);
    //     } catch (Exception ex) {
    //         logger.error("Failed to authenticate customer: {}", ex.getMessage(), ex);
    //         response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    //     }
    //     return response;
    // }
}