package com.gabriel.emplms.repository;
import org.springframework.data.repository.CrudRepository;

import com.gabriel.emplms.entity.CustomerData;
public interface CartDataRepository extends CrudRepository<CustomerData,Integer> {}