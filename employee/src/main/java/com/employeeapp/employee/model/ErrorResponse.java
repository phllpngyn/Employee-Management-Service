package com.employeeapp.employee.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String message;
}
