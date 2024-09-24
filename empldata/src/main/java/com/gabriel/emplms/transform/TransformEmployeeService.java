package com.gabriel.emplms.transform;
import com.gabriel.emplms.entity.EmployeeData;
import com.gabriel.emplms.model.Employee;
public interface TransformEmployeeService {
	EmployeeData transform(Employee employee);
	Employee transform(EmployeeData employeeData);
}
