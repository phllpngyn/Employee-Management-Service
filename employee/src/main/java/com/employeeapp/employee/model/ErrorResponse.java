package com.employeeapp.employee.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @ApiModelProperty(notes = "Code for Error Response", name = "Error Response Code", required = true)
    private int code;

    @ApiModelProperty(notes = "Message for Error Response", name = "Error Response Message", required = true)
    private String message;

    public ErrorResponse(String message) {
        super();
        this.message = message;
    }
}
