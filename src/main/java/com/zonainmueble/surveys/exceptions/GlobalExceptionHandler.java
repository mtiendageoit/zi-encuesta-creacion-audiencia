package com.zonainmueble.surveys.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AlreadyRegisterException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyRegisterException(AlreadyRegisterException ex) {
    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getCode(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }
}
