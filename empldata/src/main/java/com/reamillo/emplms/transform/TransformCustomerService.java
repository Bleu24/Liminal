package com.reamillo.emplms.transform;

import com.reamillo.emplms.entity.CustomerData;
import com.reamillo.emplms.model.Customer;
import com.reamillo.dto.CustomerSignUpDTO;

public interface TransformCustomerService {
	CustomerData transform(Customer customer);
	Customer transform(CustomerData customerData);
	CustomerData transform(CustomerSignUpDTO customerSignUpDTO);
}
