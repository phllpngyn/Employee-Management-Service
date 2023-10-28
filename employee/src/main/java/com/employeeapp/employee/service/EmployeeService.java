package com.employeeapp.employee.service;

import com.employeeapp.employee.dto.EmployeeRequest;
import com.employeeapp.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeRequest> getAllEmployees();

    EmployeeRequest createNewEmployee(Employee employee);

    EmployeeRequest getEmployeeById(long id);

    void updateEmployee(long id, Employee employee);

    void deleteEmployeeById(long id);

}
