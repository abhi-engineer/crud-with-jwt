package com.crud.controller;

import com.crud.dto.EmployeeDto;
import com.crud.exception.MyException;
import com.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get-all-employee")
    public ResponseEntity<?> getAll() throws MyException {
        List<EmployeeDto> employeeDtoList = employeeService.getAll();
        return new ResponseEntity<>(employeeDtoList, HttpStatus.OK);
    }
}
