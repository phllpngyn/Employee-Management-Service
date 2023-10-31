package com.employeeapp.employee.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.hasSize;

import com.employeeapp.employee.model.RequestErrorResponse;
import com.employeeapp.employee.model.RequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.employeeapp.employee.model.Employee;
import com.employeeapp.employee.repository.EmployeeRepository;
import com.employeeapp.employee.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.mockito.Mockito;



import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = EmployeeController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(MockitoExtension.class)
//public class EmployeeControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private EmployeeService employeeService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//}
//


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    ObjectMapper om = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;


    Employee employee1 = Employee.of(1, "phillip", "nguyen", LocalDate.of(1966, Month.DECEMBER, 5), "phillip@gmail.com", "4429557115", "Software Engineer", "Information Technology",
            "Baltimore", LocalDate.of(1999, Month.AUGUST, 1), "John Doe");
    Employee employee2 = Employee.of(2, "john", "card", LocalDate.of(2001, Month.AUGUST, 20), "john@gmail.com", "7114454355", "Software Engineer", "Information Technology",
            "Washington", LocalDate.of(2010, Month.JANUARY, 2), "John Doe");

    Employee employee3 = Employee.of(3, "brian", "ham", LocalDate.of(1989, Month.AUGUST, 22), "brian@gmail.com", "4415890070", "Tech Sales", "Sales",
            "New York", LocalDate.of(2008, Month.NOVEMBER, 22), "Cam Frane");


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void getAllEmployees_success() throws Exception {
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
        ResultActions response = mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employees)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(employees.get(1).getFirstName())));
    }

    @Test
    public void getAllEmployeesNoneAdded_success() throws Exception {
        List<Employee> employees = new ArrayList<>(Arrays.asList());
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
        ResultActions response = mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employees)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }


    @Test
    public void getEmployeeById_success() throws Exception {
//        Mockito.when(repository.findByEmployeeId(employee1.getEmployeeId())).thenReturn(Optional.ofNullable(employee1));
        Mockito.when(employeeService.getEmployeeById(employee1.getEmployeeId())).thenReturn(employee1);
        ResultActions response = mockMvc.perform(get("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employee1)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())));
    }

    @Test
    public void getEmployeeById_notFound() {
        Mockito.when(employeeService.getEmployeeById(10)).thenThrow(new RequestException(RequestErrorResponse.ID_NOT_FOUND));
        Assertions.assertThatThrownBy(() ->
                        mockMvc.perform(get("/employees/10")).andExpect(status().isNotFound()))
                .hasCause(new RequestException(null));
    }


    @Test
    public void deleteEmployeeById_success() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployeeById(1);
        ResultActions response = mockMvc.perform(delete("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employee1)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(1)));
        Mockito.verify(employeeService, times(1)).deleteEmployeeById(1);
    }

    @Test
    public void postNewEmployee_success() throws Exception {
        Mockito.when(employeeService.createNewEmployee(employee1)).thenReturn(employee1);
        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employee1)));
        response.andExpect(status().isCreated());
    }

    @Test
    public void postNewEmployee_methodArgumentNotValidException() throws Exception {
        Mockito.when(employeeService.createNewEmployee(employee1)).thenReturn(employee1);
        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employee1)));
        response.andExpect(status().isCreated());
    }

    @Test
    public void putNewEmployee_success() throws Exception {
        Mockito.when(employeeService.updateEmployee(employee1.getEmployeeId(), employee1)).thenReturn(employee1);
        ResultActions response = mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employee1)));
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(1)));;
    }

    @Test
    public void putNewEmployee_methodNotAllowed() throws Exception {
        ResultActions response = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(employee1)));
        response.andExpect(status().isMethodNotAllowed());
    }

}



