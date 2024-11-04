package com.reamillo.emplms.model;
import java.util.Date;

import lombok.Data;

@Data

public class Customer {
    private int id;
    private int age;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private Date dateOfBirth;
}
