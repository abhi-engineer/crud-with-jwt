package com.crud.dto;

import com.crud.entity.Department;
import com.crud.entity.Employee;
import com.crud.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private String email;
    private String password;
    private Department department;
    private List<Roles> rolesList;

    public Employee toEntity(){
        Employee emp = new Employee();
        emp.setName(this.getName());
        emp.setEmail(this.getEmail());
        emp.setPassword(this.getPassword());
        emp.setDepartment(this.getDepartment());
        emp.setRolesList(this.rolesList);
        return emp;
    }
}
