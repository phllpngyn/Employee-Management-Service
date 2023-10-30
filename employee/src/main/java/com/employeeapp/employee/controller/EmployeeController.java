package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.net.URI;
import java.util.List;
import java.util.Map;

@ApiOperation(value = "/employees", tags = "Employee Management Controller")
@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private EmployeeService employeeService ;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    private ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(this.employeeService.getEmployeeById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> createNewEmployee(@RequestBody Employee newEmployee, UriComponentsBuilder ucb) {
        Employee savedEmployee = this.employeeService.createNewEmployee((newEmployee));
        URI locationOfNewEmployee = ucb
                .path("employee/{id}")
                .buildAndExpand(savedEmployee.getEmployeeId())
                .toUri();
        return ResponseEntity.created(locationOfNewEmployee).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> updateEmployee(@Valid @PathVariable long id, @RequestBody Employee updatedEmployee) {
        this.employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path="/{id}")
    private ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        this.employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok().build();
    }
}
