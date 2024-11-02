package com.reamillo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerSignUpDTO {
    @Email
    @NotEmpty
    private String email;

    @Size(min = 4, message = "Username should have at least 4 characters")
    private String username;

    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @Size(min = 6, message = "Password should have at least 6 characters")
    private String retypedPassword;
}