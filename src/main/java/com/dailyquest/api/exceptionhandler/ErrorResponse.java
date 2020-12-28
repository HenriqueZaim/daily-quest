package com.dailyquest.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
    
    private Integer status;
    private OffsetDateTime timestamp;
    private String message;
    private List<FieldError> errors;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FieldError{
        private String field;
        private String message;
    }
}