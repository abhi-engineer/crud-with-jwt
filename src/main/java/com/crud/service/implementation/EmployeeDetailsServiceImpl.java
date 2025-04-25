package com.crud.service.implementation;

import com.crud.entity.EmployeeDetails;
import com.crud.entity.Employee;
import com.crud.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepo.findByEmail(username);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            return new EmployeeDetails(employee);
        }else{
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}


//✅ UserDetailsService
//Purpose: It is an interface in Spring Security used to load user-specific data.

//✅ UserDetails
//Purpose: It's an interface that represents a user in Spring Security.
//What it does:
//It tells Spring Security how to get the user's info like username, password, authorities, and account status.
