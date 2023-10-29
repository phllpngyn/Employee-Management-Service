package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @GetMapping(path = "/employees/{id}")
    private ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(this.employeeService.getEmployeeById(id));
    }

    @PostMapping(path = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> createNewEmployee(@RequestBody Employee newEmployee, UriComponentsBuilder ucb) {
        Employee savedEmployee = this.employeeService.createNewEmployee((newEmployee));
        URI locationOfNewEmployee = ucb
                .path("employee/{id}")
                .buildAndExpand(savedEmployee.getEmployeeId())
                .toUri();
        return ResponseEntity.created(locationOfNewEmployee).build();
        // if want to return employee object back?
        // return new ResponseEntity<>(employeeService.createNewEmployee(newEmployee), HttpStatus.CREATED);
    }

    @PutMapping(path = "/employees/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee) {
        this.employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path="/employees/{id}")
    private ResponseEntity<Void> deleteEmployee(@PathVariable long id) {

        try {
            this.employeeService.deleteEmployeeById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
