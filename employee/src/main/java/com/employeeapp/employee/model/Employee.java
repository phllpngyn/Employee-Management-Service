package com.employeeapp.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import javax.validation.constraints.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;



@Entity
@Table(name = "employees")
@Getter
@Setter
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ApiModel(description = "Employee Model")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    @ApiModelProperty(name = "employeeId", required = true, value = "1")
    private long employeeId;


    @NotBlank(message = "First Name shouldn't be null")
    @Column(name = "first_name")
    @ApiModelProperty(name = "firstName", required = true, value = "Kyle")
    private String firstName;

    @NotBlank(message = "Last Name shouldn't be null")
    @Column(name = "last_name")
    @ApiModelProperty(name = "lastName", required = true, value = "Long")
    private String lastName;


    @NotNull(message = "Birth date can't be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birth_date")
    @ApiModelProperty(name = "birthDate", required = true, value = "2000-10-20")
    private LocalDate birthDate;

    @Email(message = "Invalid Email Address")
    @Column(name = "email_address")
    @ApiModelProperty(name = "emailAddress", required = true, value = "kyle@gmail.com")
    private String emailAddress;

    @NotBlank(message = "Phone Number can't be empty")
    @Pattern(regexp = "^\\d{10}$",
            message = "Invalid Phone Number. Phone Number must be 10 digits with no spaces or special characters")
    @Column(name = "phone")
    @ApiModelProperty(name = "phone", required = true, value = "2919990293")
    private String phone;

    @NotNull(message = "Job Title can't be null")
    @Column(name = "job_title")
    @ApiModelProperty(name = "jobTitle", required = true, value = "tech sales")
    private String jobTitle;

    @NotNull(message = "Department can't be null")
    @Column(name = "department")
    @ApiModelProperty(name = "department", required = true, value = "IT")
    private String department;

    @NotNull(message = "Location can't be null")
    @Column(name = "location")
    @ApiModelProperty(name = "location", required = true, value = "Washington")
    private String location;

    @NotNull(message = "Start Date can't be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "start_date")
    @ApiModelProperty(name = "startDate", required = true, value = "2020-10-21")
    private LocalDate startDate;

    @NotNull(message = "Reporting Manager can't be null")
    @Column(name = "reporting_manager")
    @ApiModelProperty(name = "reportingManager", required = true, value = "John Doe")
    private String reportingManager;

}