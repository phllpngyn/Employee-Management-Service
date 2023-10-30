package com.employeeapp.employee.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    @ApiModelProperty(notes = "Code for Error Response", name = "Error Response Code", required = true)
    private final int code;

    @ApiModelProperty(notes = "Message for Error Response", name = "Error Response Message", required = true)
    private final String message;
}
