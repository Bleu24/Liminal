package com.gabriel.emplms.repository;
import org.springframework.data.repository.CrudRepository;

import com.gabriel.emplms.entity.CustomerData;
public interface CustomerDataRepository extends CrudRepository<CustomerData,Integer> {}