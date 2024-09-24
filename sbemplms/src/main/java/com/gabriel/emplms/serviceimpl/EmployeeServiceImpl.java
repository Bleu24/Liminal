package com.gabriel.emplms.serviceimpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.EmployeeData;
import com.gabriel.emplms.model.Employee;
import com.gabriel.emplms.repository.EmployeeDataRepository;
import com.gabriel.emplms.service.EmployeeService;
import com.gabriel.emplms.transform.TransformEmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	@Autowired
	EmployeeDataRepository employeeDataRepository;
	@Autowired
	TransformEmployeeService tansformerEmployeeService;
	@Override
	public Employee[] getAll() {
		List<EmployeeData> employeesData = new ArrayList<>();
		List<Employee> employees = new ArrayList<>();
		employeeDataRepository.findAll().forEach(employeesData::add);
		Iterator<EmployeeData> it = employeesData.iterator();
		while(it.hasNext()) {
			EmployeeData employeeData = it.next();
			Employee employee = tansformerEmployeeService.transform(employeeData);
			employees.add(employee);
		}
		Employee[] array = new Employee[employees.size()];
		for  (int i=0; i<employees.size(); i++){
			array[i] = employees.get(i);
		}
		return array;
	}
	@Override
	public Employee create(Employee employee) {
		logger.info(" add:Input " + employee.toString());
		EmployeeData employeeData = tansformerEmployeeService.transform(employee);
		employeeData = employeeDataRepository.save(employeeData);
		logger.info(" add:Input " + employeeData.toString());
		Employee newEmployee = tansformerEmployeeService.transform(employeeData);
		return newEmployee;
	}
	@Override
	public Employee update(Employee employee) {
		EmployeeData employeeData = tansformerEmployeeService.transform(employee);
		employeeData = employeeDataRepository.save(employeeData);
		Employee newEmployee = tansformerEmployeeService.transform(employeeData);
		return newEmployee;
	}
	@Override
	public Employee get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<EmployeeData> optional = employeeDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			EmployeeData employeeDatum = optional.get();
			Employee employee = tansformerEmployeeService.transform(employeeDatum);
			return employee;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<EmployeeData> optional = employeeDataRepository.findById(id);
		if( optional.isPresent()) {
			EmployeeData employeeDatum = optional.get();
			employeeDataRepository.delete(employeeDatum);
			logger.info(" Success >> " + employeeDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locateemployee id:" +  Integer.toString(id));
		}
	}
}
