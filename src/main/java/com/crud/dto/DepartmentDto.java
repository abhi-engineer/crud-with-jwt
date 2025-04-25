package com.crud.dto;

import com.crud.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private String deptName;

    public Department toEntity(){
        Department dept = new Department();
        dept.setDeptName(this.getDeptName());
        return dept;
    }
}
