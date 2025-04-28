package com.crud.repository;

import com.crud.dto.EmployeeDto;
import com.crud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    public Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))" )
    List<Employee> searchEmp(@Param("keyword") String keyword);
}

/*

What is JPQL?
    JPQL is a query language used in JPA (Java Persistence API) to interact with the database.
    It looks similar to SQL but works with Java classes (like Employee, Product) and their fields, not directly with table names or columns.

✏️ Key Points:
    Queries Java Entities, not database tables.
    Case-sensitive for entity and field names.
    Supports SELECT, UPDATE, DELETE operations.
    Allows joins, conditions, ordering, grouping, etc.
    Runs inside the EntityManager in Spring Boot or any JPA app.

🔥 Example:

Suppose you have a Java entity:

@Entity
public class Employee {
    @Id
    private Long id;
    private String name;
    private String department;
}

JPQL Query Example:

@Query("SELECT e FROM Employee e WHERE e.department = :dept")
List<Employee> findByDepartment(@Param("dept") String department);

Explanation:
    Employee = Java Entity
    e.department = Java field
    No table names or SQL columns!

💬 Quick Difference from SQL:
SQL	                    JPQL
Table names	            Entity names
Column names	        Field names
Works directly on DB	Works on Java objects

✅ Why Use JPQL?
    Database independent.
    Works naturally with Java classes.
    Easy to fetch, filter, update, and delete entities.

    JPQL = "SQL for Java Entities" — write queries using Java classes and fields, not tables and columns.
 */
