package com.gabriel.emplms.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gabriel.emplms.entity.CartData;

public interface CartDataRepository extends CrudRepository<CartData,Integer> {
    CartData findByUsernameAndProductId(String username, int productId);
    void deleteByUsername(String username);
    List<CartData> findByUsername(String username);
  
}