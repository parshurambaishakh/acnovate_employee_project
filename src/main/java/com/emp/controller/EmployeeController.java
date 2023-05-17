package com.emp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.emp.exception.SupervisorNotFoundException;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;

@RestController
@RequestMapping("/acnovate")
public class EmployeeController {
	

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employees")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> saveEmployee(@RequestBody Map<String, String> employee) {

		employeeService.createEmployee(employee);
		
		return new ResponseEntity<String>("Employee Data Save Successfully !", HttpStatus.CREATED);

	}

	@GetMapping("/employees/get/{name}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Map<String, String>> empDetails(@PathVariable String name) {
		Employee supervisor = employeeService.getSupervisor(name);
		if (supervisor.getSupervisor() == null) {
			throw new SupervisorNotFoundException("supervisor name not find for employee: " + name);
		}
		Map<String, String> empsupervisor = new LinkedHashMap<>();
		empsupervisor.put("employee name " + name, "supervisor name " + supervisor.getSupervisor().getName());
		return ResponseEntity.ok(empsupervisor);
	}

	@GetMapping("/employees/get/all")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Map<String, String>> getAllEmployeeDetails() {
		List<Employee> emplist = employeeService.getAll();
		List<String> list = new ArrayList<>();
		for (Employee emp : emplist) {
			list.add(emp.getName());

		}

		Map<String, String> map = new LinkedHashMap<>();

		for (String empname : list) {
			Employee supervisor = employeeService.getSupervisor(empname);
			if (supervisor.getSupervisor() == null) {
//        		map.put(empname, null);
			} else {
				map.put(empname, supervisor.getSupervisor().getName());
			}
		}

		return ResponseEntity.ok(map);
	}
}
