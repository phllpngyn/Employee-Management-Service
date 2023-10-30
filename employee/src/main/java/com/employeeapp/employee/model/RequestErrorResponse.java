package com.employeeapp.employee.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RequestErrorResponse {

    ID_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "ID Not Found");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
