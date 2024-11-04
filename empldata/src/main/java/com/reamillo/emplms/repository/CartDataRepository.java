package com.reamillo.emplms.repository;
import java.util.List;

import com.reamillo.emplms.entity.CartData;
import org.springframework.data.repository.CrudRepository;

public interface CartDataRepository extends CrudRepository<CartData,Integer> {
    CartData findByUsernameAndProductId(String username, int productId);
    void deleteByUsername(String username);
    List<CartData> findByUsername(String username);
  
}