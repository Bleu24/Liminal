package com.reamillo.emplms.repository;

import java.util.List;

import com.reamillo.emplms.entity.OrderData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDataRepository extends CrudRepository<OrderData, Integer> {
    OrderData findByUsernameAndProductId(String username, int productId);
    void deleteByUsername(String username);
    List<OrderData> findByUsername(String username);
}
