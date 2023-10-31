package com.employeeapp.employee.service;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.model.NoEmployeeExistsForIdException;
import com.employeeapp.employee.model.RequestException;
import com.employeeapp.employee.model.RequestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.employeeapp.employee.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return this.employeeRepository.findAll();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an issue with getting all employees");
        }

    }

    @Override
    public Page<Employee> getAllEmployeesByPage(int pageNo, int pageSize, String sortCategory, String sortDirection) {
        Sort sort = Sort.Direction.ASC.name().equalsIgnoreCase(sortDirection)
                ? Sort.by(sortCategory).ascending()
                : Sort.by(sortCategory).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }

    @Override
    public Employee createNewEmployee(Employee employee) {
        try {
            return this.employeeRepository.save(employee);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("There was an issue with new Employee with id: %d", employee.getEmployeeId()));
        }
    }

    @Override
    public Employee getEmployeeById(long id) {
        Employee employee = null;
        if (checkIdExists(id)) {
            Optional<Employee> employeeOptional = this.employeeRepository.findByEmployeeId(id);
            if (employeeOptional.isPresent()) {
                employee = employeeOptional.get();
                log.info("Employee was found for ID: " + id);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("There was an issue with getting the Employee details for Employee id: %d", id));
            }
        }
        return employee;
    }

    @Override
//    @Transactional
    public void deleteEmployeeById(long id) {
        if (checkIdExists(id)) {
            try {
                this.employeeRepository.deleteByEmployeeId(id);
            } catch(Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("There was an issue with deleting the Employee details for Employee id: %d", id));
            }
        }
    }

    @Override
    public Employee updateEmployee(long id, Employee newEmployeeDetails) throws ResponseStatusException {
        Employee employee = null;
        if (checkIdExists(id)) {
            Employee updatedEmployee = getEmployeeById(id);
            BeanUtils.copyProperties(newEmployeeDetails, updatedEmployee, "employeeId");
            try {
                employee = this.employeeRepository.save(updatedEmployee);
            } catch(Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("There was an issue with updating the Employee details for Employee id: %d", id));
            }
        }
        return employee;
    }


    public boolean checkIdExists(long id) throws ResponseStatusException {
        if (!employeeRepository.existsById(id)) {
            log.warn("There was issue finding id: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Employee with id: %d was not found", id));
        }
        return true;
    }
}
