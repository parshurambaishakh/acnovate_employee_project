package com.emp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.emp.model.Employee;
import com.emp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    /**
     * Method under test: {@link EmployeeController#empDetails(String)}
     */
    @Test
    void testEmpDetails() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Name");
        employee.setSupervisor(new Employee());

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Name");
        employee1.setSupervisor(employee);

        Employee employee2 = new Employee();
        employee2.setId(1L);
        employee2.setName("Name");
        employee2.setSupervisor(employee1);

        Employee employee3 = new Employee();
        employee3.setId(1L);
        employee3.setName("Name");
        employee3.setSupervisor(employee2);

        Employee employee4 = new Employee();
        employee4.setId(1L);
        employee4.setName("Name");
        employee4.setSupervisor(employee3);
        when(employeeService.getSupervisor((String) any())).thenReturn(employee4);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/acnovate/employees/get/{name}", "Name");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"employee name Name\":\"supervisor name Name\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployeeDetails()}
     */
    @Test
    void testGetAllEmployeeDetails() throws Exception {
        when(employeeService.getAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/acnovate/employees/get/all");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployeeDetails()}
     */
    @Test
    void testGetAllEmployeeDetails2() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("?");
        employee.setSupervisor(new Employee());

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("?");
        employee1.setSupervisor(employee);

        Employee employee2 = new Employee();
        employee2.setId(1L);
        employee2.setName("?");
        employee2.setSupervisor(employee1);

        Employee employee3 = new Employee();
        employee3.setId(1L);
        employee3.setName("?");
        employee3.setSupervisor(employee2);

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee3);

        Employee employee4 = new Employee();
        employee4.setId(1L);
        employee4.setName("Name");
        employee4.setSupervisor(new Employee());

        Employee employee5 = new Employee();
        employee5.setId(1L);
        employee5.setName("Name");
        employee5.setSupervisor(employee4);

        Employee employee6 = new Employee();
        employee6.setId(1L);
        employee6.setName("Name");
        employee6.setSupervisor(employee5);

        Employee employee7 = new Employee();
        employee7.setId(1L);
        employee7.setName("Name");
        employee7.setSupervisor(employee6);

        Employee employee8 = new Employee();
        employee8.setId(1L);
        employee8.setName("Name");
        employee8.setSupervisor(employee7);
        when(employeeService.getSupervisor((String) any())).thenReturn(employee8);
        when(employeeService.getAll()).thenReturn(employeeList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/acnovate/employees/get/all");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"?\":\"Name\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#saveEmployee(Map)}
     */
    @Test
    void testSaveEmployee() throws Exception {
        doNothing().when(employeeService).createEmployee((Map<String, String>) any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/acnovate/employees")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new HashMap<>()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee Data Save Successfully !"));
    }
}

