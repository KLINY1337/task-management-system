package com.chernomurov.effectivemobile.test.task.management.system.custom.exception.handler;

import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApplicationErrorMessage> catchUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationErrorMessage> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApplicationErrorMessage> catchRoleNotFoundException(RoleNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ContractorNotFoundException.class)
    public ResponseEntity<ApplicationErrorMessage> catchContractorNotFoundException(ContractorNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApplicationErrorMessage> catchTaskNotFoundException(TaskNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApplicationErrorMessage> catchUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApplicationErrorMessage> catchCustomerNotFoundException(CustomerNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnauthorizedCustomerTaskAccessException.class)
    public ResponseEntity<ApplicationErrorMessage> catchUnauthorizedCustomerTaskAccessException(UnauthorizedCustomerTaskAccessException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(UserRoleCheckException.class)
    public ResponseEntity<ApplicationErrorMessage> catchUserRoleCheckException(UserRoleCheckException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApplicationErrorMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(), HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }
}
