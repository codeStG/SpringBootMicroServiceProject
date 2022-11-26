package com.stgcodes.endpoint;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stgcodes.error.ApiError;
import com.stgcodes.exception.DataAccessException;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.IllegalPhoneDeletionException;
import com.stgcodes.exceptions.InvalidRequestBodyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        ApiError apiError = new ApiError(BAD_REQUEST, error);

        log.error(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(IdNotFoundException.class)
    protected ResponseEntity<ApiError> handleIdNotFound(IdNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    
    @ExceptionHandler(IllegalPhoneDeletionException.class)
    protected ResponseEntity<ApiError> handleIllegalPhoneDeletion(IllegalPhoneDeletionException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    protected ResponseEntity<ApiError> handleInvalidRequestBody(InvalidRequestBodyException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());
        apiError.addSubErrors(ex.getErrors().getFieldErrors());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<ApiError> handleDataAccessException(DataAccessException ex) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
