package com.employeeapp.employee.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Data
@Table(name = "employees")
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
    @Pattern(regexp="^[a-zA-Z\\s]+$",message = "Invalid Input for first name")
    @Column(name = "first_name")
    @ApiModelProperty(name = "firstName", required = true, value = "Kyle")
    private String firstName;

    @NotBlank(message = "Last Name shouldn't be null")
    @Pattern(regexp="^[a-zA-Z\\s]+$",message = "Invalid Input for last name")
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
    @Pattern(regexp="^[a-zA-Z\\s]+$",message = "Invalid Input for job title")
    @Column(name = "job_title")
    @ApiModelProperty(name = "jobTitle", required = true, value = "tech sales")
    private String jobTitle;

    @NotNull(message = "Department can't be null")
    @Pattern(regexp="^[a-zA-Z\\s]+$",message = "Invalid Input for department")
    @Column(name = "department")
    @ApiModelProperty(name = "department", required = true, value = "IT")
    private String department;

    @NotNull(message = "Location can't be null")
    @Pattern(regexp="^[a-zA-Z,\\s]+$",message = "Invalid Input for location")
    @Column(name = "location")
    @ApiModelProperty(name = "location", required = true, value = "Washington")
    private String location;

    @NotNull(message = "Start Date can't be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "start_date")
    @ApiModelProperty(name = "startDate", required = true, value = "2020-10-21")
    private LocalDate startDate;

    @NotNull
    @Column(name = "reporting_manager")
    @ApiModelProperty(name = "reportingManager", required = true, value = "3")
    private int reportingManager;
}