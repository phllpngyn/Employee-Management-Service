package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.ErrorResponse;
import com.employeeapp.employee.model.RequestErrorResponse;
import com.employeeapp.employee.model.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class EmployeeControllerAdvice {

    /* Built-in Exceptions */

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse(404, "Resource not found");
        return this.makeErrorResponse(HttpStatus.NOT_FOUND, error);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse(400, "Bad Request. Method Arguments are not valid");
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            ConstraintViolationException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse(400, ex.getLocalizedMessage());
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            ConstraintViolationException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse(400, "Bad Request. There was a type mismatch");
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }


    /* Custom Exceptions */

    @ExceptionHandler({RequestException.class})
    public ResponseEntity<ErrorResponse> handleHttpException(final RequestException exception) {
        return this.makeErrorResponse(exception.getRequestErrorResponse(), exception);
    }


    /* Helper Functions */

    /* Helper function to build the Error Response for built-in Exceptions */
    private ResponseEntity<ErrorResponse> makeErrorResponse(HttpStatus httpStatus, ErrorResponse error) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(error);
    }


    /* Helper function to build the Error Response for Custom Exceptions*/
    private ResponseEntity<ErrorResponse> makeErrorResponse(final RequestErrorResponse errorResponse, final RequestException exception) {
        log.warn("Http Request Exception occurred: " + exception);
        ErrorResponse error = new ErrorResponse(errorResponse.getCode(), errorResponse.getMessage());
        return ResponseEntity.status(errorResponse.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(error);
    }
}
