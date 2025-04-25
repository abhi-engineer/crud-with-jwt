package com.crud.service.implementation;

import com.crud.dto.EmployeeDto;
import com.crud.entity.Department;
import com.crud.entity.Employee;
import com.crud.entity.Roles;
import com.crud.exception.MyException;
import com.crud.repository.DepartmentRepo;
import com.crud.repository.EmployeeRepo;
import com.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.management.relation.RoleList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private DepartmentRepo departmentRepo;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public EmployeeDto register(EmployeeDto employeeDto) throws MyException {
        Optional<Employee> optional = employeeRepo.findByEmail(employeeDto.getEmail());
        if(optional.isPresent())
            throw new MyException("EMAIL_ALREADY_EXIST");

        //encoding password
        employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        //checking department and saving/getting into/from db
        Department department = employeeDto.getDepartment();
        Optional<Department> deptOptional = departmentRepo.findByDeptName(department.getDeptName());
        if(deptOptional.isPresent()){
            department = deptOptional.get();
        }else{
            department = departmentRepo.save(department);
        }
        employeeDto.setDepartment(department);

//        List<Roles> roleLists = employeeDto.getRolesList();

        // converting employeeDto to employee in order to save in db
        Employee employee = employeeDto.toEntity();
        employee = employeeRepo.save(employee);
        return employee.toDto();
    }

    @Override
    public List<EmployeeDto> getAll() throws MyException {
        List<Employee> employeeList = employeeRepo.findAll();
        if(employeeList.isEmpty())
            throw new MyException("NO_DATA");

        // changin Items list to ItmemsDto list, using dto method.
        List<EmployeeDto> employeeDtosList = employeeList.stream()
                .map(Employee :: toDto)
                .toList();

        return employeeDtosList;
    }

    @Override
    public EmployeeDto delete(String email) throws MyException {
        Employee employee = employeeRepo.findByEmail(email).orElseThrow(() -> new MyException("NO_DATA"));
        employeeRepo.delete(employee);
        return employee.toDto();
    }


    @Override
    public EmployeeDto update(String email, EmployeeDto employeeDto) throws MyException {
        Employee employee = employeeRepo.findByEmail(email).orElseThrow(() -> new MyException("NO_DATA"));

        if (employeeDto.getName() != null && !employeeDto.getName().isBlank())
            employee.setName(employeeDto.getName());

        if (employeeDto.getEmail() != null && !employeeDto.getEmail().isBlank())
            employee.setEmail(employeeDto.getEmail());

        if (employeeDto.getPassword() != null && !employeeDto.getPassword().isBlank())
            employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        if (employeeDto.getDepartment() != null) {
            Department department = employeeDto.getDepartment();
            Optional<Department> deptOptional = departmentRepo.findByDeptName(department.getDeptName());
            if(deptOptional.isPresent()){
                department = deptOptional.get();
            }else{
                department = departmentRepo.save(department);
            }
            employee.setDepartment(department);
        }

        if(!employeeDto.getRolesList().isEmpty()){
            employee.setRolesList(employeeDto.getRolesList());
        }

        employee = employeeRepo.save(employee);
        return employee.toDto();
    }

    @Override
    public EmployeeDto getEmployee(String email) throws MyException {
        Employee employee = employeeRepo.findByEmail(email).orElseThrow(() -> new MyException("USER_NOT_FOUND"));
        return employee.toDto();
    }


}
