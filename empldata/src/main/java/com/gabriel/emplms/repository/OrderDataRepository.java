package com.gabriel.emplms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.emplms.entity.OrderData;

@Repository
public interface OrderDataRepository extends CrudRepository<OrderData, Integer> {
    OrderData findByUsernameAndProductId(String username, int productId);
    void deleteByUsername(String username);
    List<OrderData> findByUsername(String username);
}
