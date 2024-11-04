package com.reamillo.emplms.serviceimpl;


import com.reamillo.emplms.entity.CustomerData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.reamillo.emplms.repository.CustomerDataRepository;
import com.reamillo.dto.CustomerLoginDTO;
import com.reamillo.dto.CustomerSignUpDTO;

@Service
public class AuthenticationService {

    private final CustomerDataRepository customerDataRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        CustomerDataRepository customerDataRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.customerDataRepository = customerDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerData signup(CustomerSignUpDTO input) {
        if (!input.getPassword().equals(input.getRetypedPassword())) {
            // Log the error message before throwing the exception
            logger.error("Passwords do not match for user: {}", input.getEmail());
            throw new IllegalArgumentException("Passwords do not match");
        }
        
        CustomerData user = new CustomerData();
        user.setEmail(input.getEmail());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return customerDataRepository.save(user);
    }

    public CustomerData authenticate(CustomerLoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmailOrUsername(),
                        input.getPassword()
                )
        );

        return customerDataRepository.findByEmailOrUsername(input.getEmailOrUsername())
                .orElseThrow();
    }
}
