package com.gabriel.emplms.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.emplms.model.Employee;
import com.gabriel.emplms.service.EmployeeService;
@RestController
public class EmployeeController {
	Logger logger = LoggerFactory.getLogger( EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	@GetMapping("/api/employee")
	public ResponseEntity<?> listEmployee()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Employee[] employee = employeeService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(employee);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/employee")
	public ResponseEntity<?> add(@RequestBody Employee employee){
		logger.info("Input >> " + employee.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Employee newEmployee = employeeService.create(employee);
			logger.info("created employee >> " + newEmployee.toString() );
			response = ResponseEntity.ok(newEmployee);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve employee with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/employee")
	public ResponseEntity<?> update(@RequestBody Employee employee){
		logger.info("Update Input >> employee.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Employee newEmployee = employeeService.update(employee);
			response = ResponseEntity.ok(employee);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve employee with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/employee/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input employee id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Employee employee = employeeService.get(id);
			response = ResponseEntity.ok(employee);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/employee/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			employeeService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
