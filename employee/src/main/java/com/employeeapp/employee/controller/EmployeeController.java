package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeService employeeService ;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/employees")
    private ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

}
