package com.gabriel.emplms.model;
import java.util.Date;

import lombok.Data;

@Data
public class Employee{
	int id;
	String description;
	String firstname;
	String lastname;
	int genderId;
	String genderName;
	int departmentId;
	String departmentName;
	int managerId;
	String managerName;
	String employmentStatusId;
	String employmentStatusName;
	String jobTitleId;
	String jobTitleName;
	String address;
	String phoneNumber;
	String email;
	String salary;
	Date hireDate;
	Date dateOfBirth;
}
