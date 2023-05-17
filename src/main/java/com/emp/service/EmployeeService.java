package com.emp.service;

import java.util.List;
import java.util.Map;

import com.emp.model.Employee;

public interface EmployeeService {
	public void createEmployee(Map<String, String> groupEmployee);
	public List<Employee> getAll();
	public Employee getSupervisor(String empname);
	

}
