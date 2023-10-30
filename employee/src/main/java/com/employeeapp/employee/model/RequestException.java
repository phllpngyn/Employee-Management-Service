package com.employeeapp.employee.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestException extends RuntimeException{
    private final RequestErrorResponse requestErrorResponse;
}
