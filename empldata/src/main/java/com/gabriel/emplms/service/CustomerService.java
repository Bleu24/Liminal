package com.gabriel.emplms.service;
import com.gabriel.emplms.dto.CustomerSignUpDTO;
import com.gabriel.emplms.model.Customer;

public interface CustomerService {
	Customer[] getAll() throws Exception;
	Customer get(Integer id) throws Exception;
	Customer create(Customer customer) throws Exception;
	Customer update(Customer customer) throws Exception;
	void delete(Integer id) throws Exception;
	Customer register(CustomerSignUpDTO customerSignUpDTO) throws Exception;
}
