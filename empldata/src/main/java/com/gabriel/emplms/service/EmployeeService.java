package com.gabriel.emplms.service;
import com.gabriel.emplms.model.Employee;
public interface EmployeeService {
	Employee[] getAll() throws Exception;
	Employee get(Integer id) throws Exception;
	Employee create(Employee employee) throws Exception;
	Employee update(Employee employee) throws Exception;
	void delete(Integer id) throws Exception;
}
