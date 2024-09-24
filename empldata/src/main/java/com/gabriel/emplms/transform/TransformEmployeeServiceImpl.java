package com.gabriel.emplms.transform;
import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.EmployeeData;
import com.gabriel.emplms.model.Employee;
@Service
public class TransformEmployeeServiceImpl implements TransformEmployeeService {
	@Override
	public EmployeeData transform(Employee employee){
		EmployeeData employeeData = new EmployeeData();
		employeeData.setDescription(employee.getDescription());
		employeeData.setFirstname(employee.getFirstname());
		employeeData.setLastname(employee.getLastname());
		employeeData.setGenderId(employee.getGenderId());
		employeeData.setGenderName(employee.getGenderName());
		employeeData.setDepartmentId(employee.getDepartmentId());
		employeeData.setDepartmentName(employee.getDepartmentName());
		employeeData.setManagerId(employee.getManagerId());
		employeeData.setManagerName(employee.getManagerName());
		employeeData.setEmploymentStatusId(employee.getEmploymentStatusId());
		employeeData.setEmploymentStatusName(employee.getEmploymentStatusName());
		employeeData.setJobTitleId(employee.getJobTitleId());
		employeeData.setJobTitleName(employee.getJobTitleName());
		employeeData.setAddress(employee.getAddress());
		employeeData.setPhoneNumber(employee.getPhoneNumber());
		employeeData.setEmail(employee.getEmail());
		employeeData.setSalary(employee.getSalary());
		employeeData.setHireDate(employee.getHireDate());
		employeeData.setDateOfBirth(employee.getDateOfBirth());
		return employeeData;
	}
	@Override

	public Employee transform(EmployeeData employeeData) {
		Employee employee = new Employee();
		employee.setDescription(employeeData.getDescription());
		employee.setFirstname(employeeData.getFirstname());
		employee.setLastname(employeeData.getLastname());
		employee.setGenderId(employeeData.getGenderId());
		employee.setDepartmentId(employeeData.getDepartmentId());
		employee.setDepartmentName(employeeData.getDepartmentName());
		employee.setManagerId(employeeData.getManagerId());
		employee.setManagerName(employeeData.getManagerName());
		employee.setEmploymentStatusId(employeeData.getEmploymentStatusId());
		employee.setEmploymentStatusName(employeeData.getEmploymentStatusName());
		employee.setJobTitleId(employeeData.getJobTitleId());
		employee.setJobTitleName(employeeData.getJobTitleName());
		employee.setAddress(employeeData.getAddress());
		employee.setPhoneNumber(employeeData.getPhoneNumber());
		employee.setEmail(employeeData.getEmail());
		employee.setSalary(employeeData.getSalary());
		employee.setHireDate(employeeData.getHireDate());
		employee.setDateOfBirth(employeeData.getDateOfBirth());
		return employee;
	}
}
