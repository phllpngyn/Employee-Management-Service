package com.employeeapp.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import javax.sql.DataSource;
import java.sql.SQLException;

//@EnableWebMvc
@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext springContext = SpringApplication.run(EmployeeApplication.class, args);

		DataSource dataSource = springContext.getBean(DataSource.class);
		try {
			dataSource.getConnection();
		} catch (SQLException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an issue connecting to the Database");
		}
	}
}

