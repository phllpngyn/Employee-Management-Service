package com.employeeapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String emailAddress;
    private String phone;
    private String jobTitle;
    private String department;
    private String location;
    private LocalDate startDate;
    private String reportingManager;
}