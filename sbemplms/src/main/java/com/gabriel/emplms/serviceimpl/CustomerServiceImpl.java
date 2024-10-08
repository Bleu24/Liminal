package com.gabriel.emplms.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.CustomerData;
import com.gabriel.emplms.model.Customer;
import com.gabriel.emplms.repository.CustomerDataRepository;
import com.gabriel.emplms.service.CustomerService;
import com.gabriel.emplms.transform.TransformCustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerDataRepository customerDataRepository;

    @Autowired
    TransformCustomerService transformerCustomerService;

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
}
