package com.reamillo.emplms.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.reamillo.emplms.entity.CustomerData;
import com.reamillo.emplms.model.Customer;
import com.reamillo.emplms.repository.CustomerDataRepository;
import com.reamillo.emplms.service.CustomerService;
import com.reamillo.emplms.transform.TransformCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reamillo.dto.CustomerLoginDTO;
import com.reamillo.dto.CustomerSignUpDTO;

@Service
public class CustomerServiceImpl implements CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerDataRepository customerDataRepository;

    @Autowired
    TransformCustomerService transformerCustomerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Customer[] getAll() {
        List<CustomerData> customersData = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        customerDataRepository.findAll().forEach(customersData::add);
        Iterator<CustomerData> it = customersData.iterator();
        while (it.hasNext()) {
            CustomerData customerData = it.next();
            Customer customer = transformerCustomerService.transform(customerData);
            customers.add(customer);
        }
        Customer[] array = new Customer[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            array[i] = customers.get(i);
        }
        return array;
    }

    @Override
    public Customer create(Customer customer) {
        logger.info(" add:Input " + customer.toString());
        CustomerData customerData = transformerCustomerService.transform(customer);
        customerData = customerDataRepository.save(customerData);
        logger.info(" add:Input " + customerData.toString());
        Customer newCustomer = transformerCustomerService.transform(customerData);
        return newCustomer;
    }

    @Override
    public Customer update(Customer customer) {
        CustomerData customerData = transformerCustomerService.transform(customer);
        customerData = customerDataRepository.save(customerData);
        Customer newCustomer = transformerCustomerService.transform(customerData);
        return newCustomer;
    }

    @Override
    public Customer get(Integer id) {
        logger.info(" Input id >> " + Integer.toString(id));
        Optional<CustomerData> optional = customerDataRepository.findById(id);
        if (optional.isPresent()) {
            logger.info(" Is present >> ");
            CustomerData customerDatum = optional.get();
            Customer customer = transformerCustomerService.transform(customerDatum);
            return customer;
        }
        logger.info(" Failed >> unable to locate id: " + Integer.toString(id));
        return null;
    }

    @Override
    public void delete(Integer id) {
        logger.info(" Input >> " + Integer.toString(id));
        Optional<CustomerData> optional = customerDataRepository.findById(id);
        if (optional.isPresent()) {
            CustomerData customerDatum = optional.get();
            customerDataRepository.delete(customerDatum);
            logger.info(" Success >> " + customerDatum.toString());
        } else {
            logger.info(" Failed >> unable to locate customer id:" + Integer.toString(id));
        }
    }

    @Override
    public CustomerData register(CustomerSignUpDTO customerSignUpDTO) {
        // Validate and process the data
        if (!customerSignUpDTO.getPassword().equals(customerSignUpDTO.getRetypedPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Create a new CustomerData entity
        CustomerData customerData = new CustomerData();
        customerData.setEmail(customerSignUpDTO.getEmail());
        customerData.setUsername(customerSignUpDTO.getUsername());
        customerData.setPassword(passwordEncoder.encode(customerSignUpDTO.getPassword()));

        // Save the customer entity to the database
        return customerDataRepository.save(customerData);
    }

    @Override
    public CustomerData authenticate(CustomerLoginDTO customerLoginDTO) {
    // Find the customer by email or username
    Optional<CustomerData> optionalCustomer = customerDataRepository.findByEmailOrUsername(
        customerLoginDTO.getEmailOrUsername()
    );

    // Check if the customer exists
    if (optionalCustomer.isEmpty()) {
        throw new IllegalArgumentException("User not found");
    }

    CustomerData customerData = optionalCustomer.get();

    // Verify the password
    if (!passwordEncoder.matches(customerLoginDTO.getPassword(), customerData.getPassword())) {
        throw new IllegalArgumentException("Invalid credentials");
    }

    return customerData;
    }

    
}
