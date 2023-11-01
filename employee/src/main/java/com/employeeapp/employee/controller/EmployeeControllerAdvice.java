package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class EmployeeControllerAdvice{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse2> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorResponse2 errorResponse = new ErrorResponse2(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details."
        );
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(),
                    fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse2> handleConstraintViolationException(
            ConstraintViolationException ex) {
        ErrorResponse2 error = new ErrorResponse2(400, ex.getLocalizedMessage());
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse2> handleMethodArgumentTypeMismatchException() {
        ErrorResponse2 error = new ErrorResponse2(400, "Bad Request. There was a type mismatch");
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    /* Helper Functions */
    private ResponseEntity<ErrorResponse2> makeErrorResponse(HttpStatus httpStatus, ErrorResponse2 error) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(error);
    }

//    private ResponseEntity<ErrorResponse2> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus, WebRequest request) {
//        ErrorResponse2 errorResponse = new ErrorResponse2(
//                httpStatus.value(),
//                exception.getMessage()
//        );
//        return ResponseEntity.status(httpStatus).body(errorResponse);
//    }

}
