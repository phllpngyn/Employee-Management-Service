package com.employeeapp.employee.model;

public class NoEmployeeExistsForIdException extends RuntimeException {
    private String message;

    public NoEmployeeExistsForIdException() {}

    public NoEmployeeExistsForIdException(String msg)
        {
            super(msg);
            this.message = msg;
        }
    }