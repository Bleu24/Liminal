package com.reamillo.emplms.repository;
import java.util.Optional;

import com.reamillo.emplms.entity.CustomerData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerDataRepository extends CrudRepository<CustomerData,Integer> {
    @Query("SELECT c FROM CustomerData c WHERE c.email = :emailOrUsername OR c.username = :emailOrUsername")
    Optional<CustomerData> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}