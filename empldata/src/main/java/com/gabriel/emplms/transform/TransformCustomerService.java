package com.gabriel.emplms.transform;
import com.gabriel.emplms.entity.CustomerData;
import com.gabriel.emplms.model.Customer;
public interface TransformCustomerService {
	CustomerData transform(Customer customer);
	Customer transform(CustomerData customerData);
}
