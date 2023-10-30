//package com.employeeapp.employee.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.util.Predicates;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                .apis(Predicates.negate(RequestHandlerSelectors.basePackage("org.springframework.boot"))).build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("Employee Management System")
//                .description("This API can be used to get employee data from the Employee Management System")
//                .version("V1.0").build();
//    }
//}
