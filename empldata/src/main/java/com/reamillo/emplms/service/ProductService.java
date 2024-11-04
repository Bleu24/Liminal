package com.reamillo.emplms.service;

import com.reamillo.emplms.model.Product;

public interface ProductService {
	Product[] getAll() throws Exception;
	Product get(Integer id) throws Exception;
	Product create(Product product) throws Exception; 
	Product update(Product product) throws Exception; 
	void delete(Integer id) throws Exception;
}
