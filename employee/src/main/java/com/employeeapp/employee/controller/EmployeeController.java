package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.model.ErrorResponse;
import com.employeeapp.employee.model.ErrorResponse2;
import com.employeeapp.employee.model.PageBody;
import com.employeeapp.employee.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@ApiOperation(value = "/employees", tags = "Employee Management Controller")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService ;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "Get All Employees", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Iterable.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @GetMapping
    private ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Iterable.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class),
    })
    @GetMapping(path = "/page")
     private ResponseEntity<Page<Employee>> getAllEmployeesByPage(@RequestBody PageBody pageBody) {
        return ResponseEntity.ok(this.employeeService.getAllEmployeesByPage(pageBody.getPageNo(), pageBody.getPageSize(),
                pageBody.getSortField(), pageBody.getSortDirection()));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Employee.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class),
    })
    @ApiOperation(value = "Get Employee By Id", response = Employee.class)
    @GetMapping(path = "/{id}")
    private ResponseEntity<Employee> getEmployeeById(@PathVariable @Valid long id) {
        return ResponseEntity.ok(this.employeeService.getEmployeeById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = Employee.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class),
    })
    @ApiOperation(value = "Create New Employee")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> createNewEmployee(
            @RequestBody @Valid Employee newEmployee, UriComponentsBuilder ucb) {
        Employee savedEmployee = this.employeeService.createNewEmployee(newEmployee);
        URI locationOfNewEmployee = ucb
                .path("employee/{id}")
                .buildAndExpand(savedEmployee.getEmployeeId())
                .toUri();
        return ResponseEntity.created(locationOfNewEmployee).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT", response = Employee.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class),
    })
    @ApiOperation(value = "Updating Employee Details")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Long> updateEmployee(@PathVariable @Valid long id, @RequestBody @Valid Employee updatedEmployee) {
        this.employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT", response = Employee.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class),
    })
    @ApiOperation(value = "Deleting Employee")
    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    private ResponseEntity<Long> deleteEmployee(@PathVariable @Valid long id) {
        this.employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(id);
    }

}
