package com.gabriel.emplms.repository;
import org.springframework.data.repository.CrudRepository;

import com.gabriel.emplms.entity.CartData;
public interface CartDataRepository extends CrudRepository<CartData,Integer> {}