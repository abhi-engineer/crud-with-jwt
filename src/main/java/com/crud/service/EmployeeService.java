package com.crud.service;

import com.crud.dto.EmployeeDto;
import com.crud.exception.MyException;
import java.util.List;

public interface EmployeeService {
    public EmployeeDto register(EmployeeDto employeeDto) throws MyException;
    public List<EmployeeDto> getAll() throws MyException;
    public EmployeeDto delete(String email) throws MyException;
    public EmployeeDto update(String email, EmployeeDto employeeDto) throws MyException;
    public EmployeeDto getEmployee(String email) throws MyException;
}
