package com.gabriel.emplms.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "employee_data")
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;
    private String name;
    private String firstname;
    private String lastname;
    private int genderId;
    private String genderName;
    private int managerId;
    private String managerName;
    private String employmentStatusId;
    private String employmentStatusName;
    String jobTitleId;
    String jobTitleName;
    String address;
    String phoneNumber;
    String email;
    String salary;
    private int departmentId;
    private String departmentName;
    Date hireDate;
    Date dateOfBirth;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date lastUpdated;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created;

}
