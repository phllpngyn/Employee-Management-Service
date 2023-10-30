package com.employeeapp.employee.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.repository.EmployeeRepository;
import com.employeeapp.employee.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository repository;




    @Test
    public void getAllEmployeesTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(Employee.of(1, "phillip", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "john@gmail.com", "4429557115", "Software Engineer", "Information Technology",
                                "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe"),
                        Employee.of(1, "phillip", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "john@gmail.com", "4429557115", "Software Engineer", "Information Technology",
                                "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe"))
                .collect(Collectors.toList()));
        List<Employee> employees = employeeService.getAllEmployees();
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    public void updateEmployeeTest() throws CloneNotSupportedException{
        Employee employee = Employee.of(1, "phillip", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "john@gmail.com", "4429557115", "Software Engineer", "Information Technology",
                "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe");
        when(repository.save(employee)).thenReturn(employee);
        Employee newEmployee = employeeService.createNewEmployee(employee);
        assertNotNull(newEmployee);
        assertEquals(employee, newEmployee);

        Employee updatedEmployee = Employee.of(1, "john", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "john@gmail.com", "4429557115", "Software Engineer", "Information Technology",
                "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe");
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);
        when(repository.existsById(newEmployee.getEmployeeId())).thenReturn(true);
        when(repository.findByEmployeeId(newEmployee.getEmployeeId())).thenReturn(Optional.of(employee));
        long employeeId = newEmployee.getEmployeeId();
        updatedEmployee = employeeService.updateEmployee(employeeId, updatedEmployee);
        assertNotNull(updatedEmployee);
        assertNotEquals("phillip", updatedEmployee.getFirstName());
    }

    @Test
    public void getEmployeeById() {
        long id = 1;
        when(repository.findByEmployeeId(1)).thenReturn(Optional.ofNullable(Employee.of(1, "phillip", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "john@gmail.com", "4429557115", "Software Engineer", "Information Technology",
                "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe")));
        when(repository.existsById(id)).thenReturn(true);
        Employee employee = employeeService.getEmployeeById(id);
        assertNotNull(employee);

    }

    @Test
    public void deleteEmployee() {
        Employee employee = Employee.of(1, "phillip", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "john@gmail.com", "4429557115", "Software Engineer", "Information Technology",
                "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe");
        when(repository.existsById(employee.getEmployeeId())).thenReturn(true);
        employeeService.deleteEmployeeById(employee.getEmployeeId());
        verify(repository, times(1)).deleteByEmployeeId(employee.getEmployeeId());
    }
}