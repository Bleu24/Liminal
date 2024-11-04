package com.gabriel.emplms.repository;
import org.springframework.data.repository.CrudRepository;

import com.gabriel.emplms.entity.ProductData;
public interface ProductDataRepository extends CrudRepository<ProductData,Integer> {}