package com.gabriel.emplms.repository;
import org.springframework.data.repository.CrudRepository;

import com.gabriel.emplms.entity.EmployeeData;
public interface EmployeeDataRepository extends CrudRepository<EmployeeData,Integer> {}