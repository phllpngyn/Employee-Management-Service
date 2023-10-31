package com.employeeapp.employee.controller;

import com.employeeapp.employee.model.ErrorResponse;
import com.employeeapp.employee.model.NoEmployeeExistsForIdException;
import com.employeeapp.employee.model.RequestErrorResponse;
import com.employeeapp.employee.model.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class EmployeeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(400, ex.getLocalizedMessage());
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException() {
        ErrorResponse error = new ErrorResponse(400, "Bad Request. There was a type mismatch");
        return this.makeErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler({NoEmployeeExistsForIdException.class})
    public ResponseEntity<ErrorResponse> handleNoEmployeeExistsForIdException(
            NoEmployeeExistsForIdException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getLocalizedMessage());
        return this.makeErrorResponse(HttpStatus.NOT_FOUND, error);
    }

    /* Helper Functions */
    private ResponseEntity<ErrorResponse> makeErrorResponse(HttpStatus httpStatus, ErrorResponse error) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(error);
    }


    /* Helper function to build the Error Response for Custom Exceptions*/
//    private ResponseEntity<ErrorResponse> makeErrorResponse(final RequestErrorResponse errorResponse, final RequestException exception) {
//        log.warn("Http Request Exception occurred: " + exception);
//        ErrorResponse error = new ErrorResponse(errorResponse.getCode(), errorResponse.getMessage());
//        return ResponseEntity.status(errorResponse.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(error);
//    }
}
