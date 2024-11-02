// package com.reamillo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import static org.springframework.security.config.Customizer.withDefaults;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;


// @Configuration
// @Order(2)
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//         http.csrf(csrf -> csrf.disable()) // Disable CSRF for testing purposes
//                 .cors(withDefaults()) // Enable CORS
//                 .authorizeRequests(requests -> requests
//                         .antMatchers("/api/product/**", "/api/customer/**", "/api/cart/**", "/api/register/**").permitAll() // Allow access to all sub-paths
//                         .anyRequest().authenticated()); // Secure all other endpoints

//         return http.build();
//     }
    
//     // @Bean
//     // public CorsFilter corsFilter() {
//     //     CorsConfiguration config = new CorsConfiguration();
//     //     config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  // Allow the frontend origin
//     //     config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Include OPTIONS for preflight
//     //     config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));  // Allow common headers
//     //     config.setAllowCredentials(true);  // Enable credentials if required

//     //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     //     source.registerCorsConfiguration("/**", config); // Apply CORS settings to all endpoints

//     //     return new CorsFilter(source);
//     // }
// }
