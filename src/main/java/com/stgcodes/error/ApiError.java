package com.stgcodes.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime timestamp;
    private String message;
    private List<ApiSubError> subErrors;
    @JsonIgnore
    private ResourceBundleMessageSource messageSource;

    private ApiError() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        timestamp = LocalDateTime.now();
        subErrors = new ArrayList<>();
    }

    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }


    public void addSubError(String object, String field, Object rejectedValue, String message) {
        subErrors.add(new ApiSubError(object, field, rejectedValue, message));
    }

    private void addSubError(FieldError fieldError) {
        String errorCode = fieldError.getCode();
        String errorMessage = errorCode != null ? messageSource.getMessage(errorCode, null, Locale.US) : fieldError.getDefaultMessage();

        this.addSubError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                errorMessage);
    }

    public void addSubErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addSubError);
    }
}
