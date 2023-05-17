package com.emp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emp.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByName(String name);
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM employees WHERE name = :name"
    		, nativeQuery = true)
    boolean isPresent(@Param("name") String name);

}

