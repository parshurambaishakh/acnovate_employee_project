package com.emp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.exception.EmployeeNameNotFoudException;
import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeRepository empRepo;

	@Override
	public void createEmployee(Map<String, String> groupEmployee) {
		for (Map.Entry<String, String> entry : groupEmployee.entrySet()) {
            String employeeName = entry.getKey();
            String supervisorName = entry.getValue();

            Employee employee = new Employee();
            if(!empRepo.isPresent(supervisorName)) {
            	employee.setName(supervisorName);
            	empRepo.save(employee);
            }
        }
        for (Map.Entry<String, String> entry : groupEmployee.entrySet()) {
            String employeeName = entry.getKey();
            String supervisorName = entry.getValue();

            Employee employee = new Employee();
            if(empRepo.isPresent(employeeName)) {
            	Employee emp = empRepo.findByName(employeeName);
            	emp.setSupervisor(empRepo.findByName(supervisorName));;
            	empRepo.save(emp);
            }else {
            	employee.setName(employeeName);
            	employee.setSupervisor(empRepo.findByName(supervisorName));
            	empRepo.save(employee);
            }
        }
		
	}

	

	@Override
	public Employee getSupervisor(String empname) {
		if(!empRepo.isPresent(empname)){
			throw new EmployeeNameNotFoudException("Employee name "+empname+" not find ");
		}
		Employee employee = empRepo.findByName(empname);
		return employee;
	}
	
	@Override
	public List<Employee> getAll() {
		
		return empRepo.findAll();
	}

}
