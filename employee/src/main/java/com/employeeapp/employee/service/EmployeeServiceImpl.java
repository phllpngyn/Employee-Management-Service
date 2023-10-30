package com.employeeapp.employee.service;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.model.RequestException;
import com.employeeapp.employee.model.RequestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.employeeapp.employee.repository.EmployeeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        Employee employee = null;
        if (checkIdExists(id)) {
            Optional<Employee> employeeOptional = this.employeeRepository.findByEmployeeId(id);
            if (employeeOptional.isPresent()) {
                employee = employeeOptional.get();
            } else {
                throw new RequestException(RequestErrorResponse.ID_NOT_FOUND);
            }
        }
        return employee;
    }

    @Override
    @Transactional
    public void deleteEmployeeById(long id) {
        if (checkIdExists(id)) {
            this.employeeRepository.deleteByEmployeeId(id);
        }
    }

    @Override
    public Employee updateEmployee(long id, Employee newEmployeeDetails) {
        Employee employee = null;
        if (checkIdExists(id)) {
            Employee updatedEmployee = getEmployeeById(id);
            BeanUtils.copyProperties(newEmployeeDetails, updatedEmployee, "employeeId");
            employee = this.employeeRepository.save(updatedEmployee);
        }
        return employee;
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

    public boolean checkIdExists(long id) throws RequestException {
        if (!employeeRepository.existsById(id)) {
            log.warn("There was issue finding id: " + id);
            throw new RequestException(RequestErrorResponse.ID_NOT_FOUND);
        }
        return true;
    }
}
