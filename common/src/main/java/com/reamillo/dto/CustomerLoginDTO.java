package com.reamillo.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CustomerLoginDTO {
    @NotEmpty(message = "Email or Username cannot be empty")
    private String emailOrUsername;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
