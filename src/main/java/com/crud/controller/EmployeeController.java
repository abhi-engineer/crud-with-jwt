package com.crud.controller;

import com.crud.dto.EmployeeDto;
import com.crud.exception.MyException;
import com.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> register(@RequestBody EmployeeDto employeeDto) throws MyException {
        employeeDto = employeeService.register(employeeDto);
        return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.CREATED);
    }

    @GetMapping("/get-employee")
    public ResponseEntity<?> getEmployee() throws MyException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        EmployeeDto employeeDto = employeeService.getEmployee(username);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete () throws MyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        EmployeeDto employeeDto = employeeService.delete(username);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update (@RequestBody EmployeeDto employeeDto) throws MyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        employeeDto = employeeService.update(username, employeeDto);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
}
