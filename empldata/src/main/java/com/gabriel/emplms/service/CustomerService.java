package com.gabriel.emplms.service;

import com.gabriel.emplms.entity.CustomerData;
import com.gabriel.emplms.model.Customer;
import com.reamillo.dto.CustomerSignUpDTO;

public interface CustomerService {
	Customer[] getAll() throws Exception;
	Customer get(Integer id) throws Exception;
	Customer create(Customer customer) throws Exception; //profile edit
	Customer update(Customer customer) throws Exception; //profile edit
	void delete(Integer id) throws Exception;
	CustomerData register(CustomerSignUpDTO customerSignUpDTO) throws Exception;
}
