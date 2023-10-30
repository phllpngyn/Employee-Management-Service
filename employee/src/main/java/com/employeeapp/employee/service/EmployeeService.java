package com.employeeapp.employee.service;

import com.employeeapp.employee.dto.EmployeeRequest;
import com.employeeapp.employee.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee createNewEmployee(Employee employee);

    Employee getEmployeeById(long id);
    Employee updateEmployee(long id, Employee employee);

    void deleteEmployeeById(long id);

}
