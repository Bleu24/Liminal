package com.reamillo.emplms.transform;

import com.reamillo.emplms.entity.CustomerData;
import com.reamillo.emplms.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reamillo.dto.CustomerSignUpDTO;

@Service
public class TransformCustomerServiceImpl implements TransformCustomerService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public CustomerData transform(Customer customer) {
        CustomerData customerData = new CustomerData();
        customerData.setId(customer.getId());
        customerData.setAge(customer.getAge());
        customerData.setUsername(customer.getUsername());
        customerData.setFirstname(customer.getFirstname());
        customerData.setLastname(customer.getLastname());
        customerData.setEmail(customer.getEmail());
        customerData.setPhoneNumber(customer.getPhoneNumber());
        customerData.setAddress(customer.getAddress());
        customerData.setDateOfBirth(customer.getDateOfBirth());
        customerData.setPassword(customer.getPassword());
        return customerData;
    }

    @Override
    public Customer transform(CustomerData customerData) {
        Customer customer = new Customer();
        customer.setId(customerData.getId());
        customer.setAge(customerData.getAge());
        customer.setUsername(customerData.getUsername());
        customer.setFirstname(customerData.getFirstname());
        customer.setLastname(customerData.getLastname());
        customer.setEmail(customerData.getEmail());
        customer.setPhoneNumber(customerData.getPhoneNumber());
        customer.setAddress(customerData.getAddress());
        customer.setDateOfBirth(customerData.getDateOfBirth());
        return customer;
    }

    @Override
    public CustomerData transform(CustomerSignUpDTO customerSignUpDTO) {
        CustomerData customerData = new CustomerData();
        customerData.setEmail(customerSignUpDTO.getEmail());
        customerData.setPassword(passwordEncoder.encode(customerSignUpDTO.getPassword())); // Hash the password
        return customerData;
    }
}