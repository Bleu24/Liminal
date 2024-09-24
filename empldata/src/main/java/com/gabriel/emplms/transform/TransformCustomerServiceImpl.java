package com.gabriel.emplms.transform;

import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.CustomerData;
import com.gabriel.emplms.model.Customer;

@Service
public class TransformCustomerServiceImpl implements TransformCustomerService {
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
        customerData.setCustomerType(customer.getCustomerType());
        customerData.setStatus(customer.getStatus());
        customerData.setDateOfBirth(customer.getDateOfBirth());
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
        customer.setCustomerType(customerData.getCustomerType());
        customer.setStatus(customerData.getStatus());
        customer.setDateOfBirth(customerData.getDateOfBirth());
        return customer;
    }
}