package com.foxminded.hotel.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundHandler(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<Object> duplicateEntryHandler(DuplicateEntryException ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
