package com.crud.entity;

import com.crud.dto.DepartmentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deptId;
    @Column(unique = true)
    private String deptName;

    public DepartmentDto toDto(){
        return new DepartmentDto(this.deptName);
    }
}
