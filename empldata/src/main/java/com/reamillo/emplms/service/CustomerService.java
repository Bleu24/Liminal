package com.reamillo.emplms.service;

import com.reamillo.emplms.entity.CustomerData;
import com.reamillo.emplms.model.Customer;
import com.reamillo.dto.CustomerLoginDTO;
import com.reamillo.dto.CustomerSignUpDTO;

public interface CustomerService {
	Customer[] getAll() throws Exception;
	Customer get(Integer id) throws Exception;
	Customer create(Customer customer) throws Exception; //profile edit
	Customer update(Customer customer) throws Exception; //profile edit
	void delete(Integer id) throws Exception;
	CustomerData register(CustomerSignUpDTO customerSignUpDTO) throws Exception;
	CustomerData authenticate(CustomerLoginDTO customerLoginDTO) throws Exception;
}
