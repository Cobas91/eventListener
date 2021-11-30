/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.controller.exception;

import eventlistener.exception.ActionNotConfiguredException;
import eventlistener.exception.EventNotFoundException;
import eventlistener.exception.OfficeAccessTokenException;
import eventlistener.exception.OfficeOAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException ex){
        log.error("Resource not found!",ex);
        ApiError apiError = new ApiError("Resource not found!", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex){
        log.error("Given input is not processable!",ex);
        ApiError apiError = new ApiError("Given input is not processable!", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex){
        log.error("Username and Password is not valid",ex);
        ApiError apiError = new ApiError("Username and Password is not valid", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
        log.error("No valid Authentication",ex);
        ApiError apiError = new ApiError("No valid Authentication", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiError> handleSignatureException(SignatureException ex){
        log.error("No valid Token",ex);
        ApiError apiError = new ApiError("No valid Token", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<ApiError> handleMappingException(MappingException ex){
        log.error("Could not handle Resource job. ",ex);
        ApiError apiError = new ApiError("Could not handle Resource job. ", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiError> handleMailException(MailException ex){
        log.error("Could not send Mail",ex);
        ApiError apiError = new ApiError("Could not send Mail", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(OfficeOAuthException.class)
    public ResponseEntity<ApiError> handleOfficeOAuthException(OfficeOAuthException ex){
        log.error("No valid Office User Informations",ex);
        ApiError apiError = new ApiError("No valid Office User Informations", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OfficeAccessTokenException.class)
    public ResponseEntity<ApiError> handleOfficeAccessTokenException(OfficeAccessTokenException ex){
        log.error("Office OAuth Code is not unprocessable",ex);
        ApiError apiError = new ApiError("Office OAuth Code is not unprocessable", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ActionNotConfiguredException.class)
    public ResponseEntity<ApiError> handleActionNotConfiguredException(ActionNotConfiguredException ex){
        log.error("Action is not Configured yet.",ex);
        ApiError apiError = new ApiError("Action is not Configured yet.", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiError> handleEventNotFoundException(EventNotFoundException ex){
        log.error("Event does not exists",ex);
        ApiError apiError = new ApiError("Event does not exists", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleUnknownException(Throwable ex){
        log.error("Server error!",ex);
        ApiError apiError = new ApiError("Server error!", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


}
