package com.dailyquest.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.dailyquest.domain.services.exceptions.AuthorizationException;
import com.dailyquest.domain.services.exceptions.DataIntegrityException;
import com.dailyquest.domain.services.exceptions.DomainException;
import com.dailyquest.domain.services.exceptions.FileException;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

    private ErrorResponse errorResponse = new ErrorResponse();

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> authorizationExceptionHandler(AuthorizationException ex, WebRequest request){
        var status = HttpStatus.FORBIDDEN;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<Object> FileExceptionHandler(FileException ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<Object> AmazonServiceExceptionHandler(AmazonServiceException ex, WebRequest request){
        HttpStatus code = HttpStatus.valueOf(ex.getErrorCode());

        errorResponse.setStatus(code.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), code, request);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<Object> AmazonClientExceptionHandler(AmazonClientException ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<Object> AmazonS3ExceptionHandler(AmazonS3Exception ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> domainExceptionHandler(DomainException ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<Object> dataIntegrityExceptionHandler(DataIntegrityException ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> ObjectNotFoundExceptionHandler(ObjectNotFoundException ex, WebRequest request){
        var status = HttpStatus.NOT_FOUND;
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMoment(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }
 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        for(ObjectError error : ex.getBindingResult().getAllErrors()){
            fieldErrors.add(new ErrorResponse.FieldError(
                ((FieldError)error).getField(), 
                messageSource.getMessage(error, LocaleContextHolder.getLocale()))
            );
        }

        errorResponse.setErrors(fieldErrors);
        errorResponse.setStatus(status.value());
        errorResponse.setMessage("Um ou mais campos estão inválidos");
        errorResponse.setMoment(OffsetDateTime.now());
        
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }
}