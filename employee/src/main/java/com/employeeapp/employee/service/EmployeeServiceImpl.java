package com.employeeapp.employee.service;

import com.employeeapp.employee.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.employeeapp.employee.repository.EmployeeRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee createNewEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> employeeOptional = this.employeeRepository.findByEmployeeId(id); //.orElseThrow(() -> new PostException(PostErrorResult.ID_NOT_FOUND));
        Employee employee = null;
        if (employeeOptional.isPresent()) {
            employee = employeeOptional.get();
            //return ResponseEntity.ok(employee);
        } else {
            throw new RuntimeException("Employee not found for id: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteByEmployeeId(id);
    }

    @Override
    public void updateEmployee(long id, Employee newEmployeeDetails) {
        Employee updatedEmployee = getEmployeeById(id);
        BeanUtils.copyProperties(newEmployeeDetails, updatedEmployee, "employeeId");
//        updatedEmployee.setEmployeeId(id);
        this.employeeRepository.save(updatedEmployee);
    }

//    private Employee updateFields(Employee employee,Employee newEmployeeDetails) {
//        employee.setFirstName(newEmployeeDetails.getFirstName());
//        employee.setLastName(newEmployeeDetails.getLastName());
//        employee.setBirthDate(newEmployeeDetails.getBirthDate());
//        employee.setEmailAddress(newEmployeeDetails.get);
//        employee.
//
//        return Employee
//    }

}
