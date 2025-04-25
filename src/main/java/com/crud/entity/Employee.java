package com.crud.entity;

import com.crud.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Roles> rolesList;


    public EmployeeDto toDto(){
        return new EmployeeDto(this.name, this.email, this.password, this.department, this.rolesList);
    }
}

//Creates a separate table (like employee_roles) with the employee_id and role columns.
//Stores enum names as strings (e.g., "ADMIN"), not as ordinal numbers.
// @Enumerated(EnumType.STRING) , it will let the store enum as string in the db
// @ElementCollection(fetch = FetchType.EAGER) , it will let to store list of enum
// JPA by default can’t handle collections of non-entity values (like enums, strings, integers) without help.

// @ElementCollection tells JPA:
//    "This collection is not a separate entity, but I still want you to persist it in a separate table and manage it automatically."
// when using @ElementCollection, it does not create a column in the main entity's table.