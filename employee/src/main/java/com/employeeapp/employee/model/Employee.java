package com.employeeapp.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import javax.validation.constraints.*;

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
//@SecondaryTables({
//    @SecondaryTable(name = "contacts", @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "employee_id")),
//    @SecondaryTable(name = "work", @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "employee_id"))
//})

public class Employee  { //implements Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private long employeeId;


    @NotBlank(message = "First Name shouldn't be null")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last Name shouldn't be null")
    @Column(name = "last_name")
    private String lastName;


    @NotNull(message = "Birth date can't be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Email(message = "Invalid Email Address")
    @Column(name = "email_address")
    private String emailAddress;

    @NotBlank(message = "Phone Number can't be empty")
    @Pattern(regexp = "^\\d{10}$",
            message = "Invalid Phone Number. Phone Number must be 10 digits with no spaces or special characters")
    @Column(name = "phone")
    private String phone;

    @NotNull(message = "Job Title can't be null")
    @Column(name = "job_title")
    private String jobTitle;

    @NotNull(message = "Department can't be null")
    @Column(name = "department")
    private String department;

    @NotNull(message = "Location can't be null")
    @Column(name = "location")
    private String location;

    @NotNull(message = "Start Date can't be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    //make the manager id thats many to 1 to manager table
    @NotNull(message = "Reporting Manager can't be null")
    @Column(name = "reporting_manager")
    private String reportingManager;



//    @OneToOne(mappedBy = "employees")
//    ContactDetails contactDetails;
//
//    @OneToOne(mappedBy = "employees")
//    WorkDetails workDetails;


    //@transactional? I forgot the name

    //getters and setters

}

//@Embeddable
//public class contact {
//    private String phone_number;
//    private String email_address;
//
//}