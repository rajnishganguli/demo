package com.example.demo;

import com.example.demo.bean.ErrorDetails;
import com.example.demo.bean.response.Response;
import com.example.demo.constants.CommonConstants;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleAllExceptions(Exception ex, WebRequest request){
        Response failureResponse = new Response(CommonConstants.STATUS_FAILURE);
        failureResponse.setErrorDetails(Arrays.asList(new ErrorDetails(CommonConstants.EXCEPTION_MESSAGE_SOMETHING_WENT_WRONG, LocalDateTime.now())));
        return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Response> handleValidationException(Exception ex, WebRequest request){
        Response failureResponse = new Response(CommonConstants.STATUS_FAILURE);
        failureResponse.setErrorDetails(Arrays.asList(new ErrorDetails(ex.getMessage(), LocalDateTime.now())));
        return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class})
    public final ResponseEntity<Response> handleUserNotFoundException(Exception ex, WebRequest request){
        Response failureResponse = new Response(CommonConstants.STATUS_FAILURE);
        failureResponse.setErrorDetails(Arrays.asList(new ErrorDetails(ex.getMessage(), LocalDateTime.now())));
        return new ResponseEntity<>(failureResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Response> handleDataIntegrityException(Exception ex, WebRequest request){
        Response failureResponse = new Response(CommonConstants.STATUS_FAILURE);
        failureResponse.setErrorDetails(Arrays.asList(new ErrorDetails(ex.getMessage(), LocalDateTime.now())));
        return new ResponseEntity<>(failureResponse, HttpStatus.CONFLICT);
    }



}
