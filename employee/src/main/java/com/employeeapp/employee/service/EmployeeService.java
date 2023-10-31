package com.employeeapp.employee.service;

import com.employeeapp.employee.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Page<Employee> getAllEmployeesByPage(int pageNo, int pageSize, String sortField, String sortDirection);

    Employee createNewEmployee(Employee employee);

    Employee getEmployeeById(long id);
    Employee updateEmployee(long id, Employee employee);

    void deleteEmployeeById(long id);

}
