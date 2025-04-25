package com.crud.config;

import com.crud.entity.Employee;
import com.crud.service.implementation.EmployeeDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private EmployeeDetailsServiceImpl employeeDetailsServiceImpl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/employee/register").permitAll()
                        .requestMatchers("/employee/getAll").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // Create an instance of DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Set the password encoder to ensure password hashing/encoding
        provider.setPasswordEncoder(passwordEncoder());
        // Set the UserDetailsService to fetch user details (username, password, roles) from the database
        provider.setUserDetailsService(employeeDetailsServiceImpl);
        // Return the configured AuthenticationProvider
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
/*

-> SecurityFilterChain for Configuring Security
-> UserDetailsService to Load User => You implement this to tell Spring how to fetch users (from DB, etc.)
-> UserDetails to Represent Authenticated User
-> PasswordEncoder Bean for Password Hashing => You must define a PasswordEncoder, or Spring will reject login.
-> HttpSecurity object => It tells :
                       What endpoints are public
                       What roles are required for access
                       What type of authentication to use (form login, basic auth, JWT, etc.)
                       CORS, CSRF, session management, headers, etc.

-> AuthenticationManager => Receives an Authentication request (typically UsernamePasswordAuthenticationToken),
                            and delegates the authentication process to one or more AuthenticationProviders.

-> AuthenticationProvider =>  is an interface in Spring Security that performs authentication logic.
    It takes user credentials (like username & password), and returns an authenticated Authentication object
    if the credentials are valid.

-> SecurityContextHolder => is a central class in Spring Security that holds the SecurityContext for the current
    thread of execution. The SecurityContext stores the authenticated user's Authentication object,
    which contains details about the user (like username, roles, and granted authorities).

 :: HttpSecurity <-> SecurityFilterChain (intercept the request) <-> AuthenticationProvider <-> AuthenticationManager <-> UserDetailsService <-> Db

[Client (Browser/Postman)]
        |
        v
[HTTP Request (Username & Password)]
        |
        v
[Spring Security FilterChain]
        |
        v
[UsernamePasswordAuthenticationFilter]
        |
        v
[AuthenticationManager]
        |
        v
[AuthenticationProvider] --------- The AuthenticationProvider validates credentials by using a UserDetailsService and a PasswordEncoder.
        |
        v
[UserDetailsService] <---------> [Database (User & Roles)]
        |
        v
[UserDetails]
        |
        v
[PasswordEncoder (e.g., BCrypt)]
        |
        v
[Authenticated Token (UsernamePasswordAuthenticationToken)]
        |
        v
[SecurityContextHolder]
        |
        v
[HTTP Response -> Client]

 */

