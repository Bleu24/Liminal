package com.gabriel.emplms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.emplms.entity.CustomerData;
import com.gabriel.emplms.serviceimpl.AuthenticationService;
import com.gabriel.emplms.serviceimpl.JwtService;
import com.reamillo.dto.CustomerLoginDTO;
import com.reamillo.dto.CustomerSignUpDTO;
import com.reamillo.dto.LoginResponse;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerData> register(@RequestBody CustomerSignUpDTO customerSignUpDTO) {
        CustomerData customerData = authenticationService.signup(customerSignUpDTO);

        return ResponseEntity.ok(customerData);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody CustomerLoginDTO customerLoginDTO) {
        CustomerData authenticatedUser = authenticationService.authenticate(customerLoginDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
        .username(jwtService.extractUsername(jwtToken))
        .token(jwtToken)
        .expiresIn(jwtService.getExpirationTime())
        .build();

        return ResponseEntity.ok(loginResponse);
    }
}