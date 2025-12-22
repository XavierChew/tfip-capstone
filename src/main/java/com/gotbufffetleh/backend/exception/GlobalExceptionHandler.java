package com.gotbufffetleh.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
// https://medium.com/@sharmapraveen91/handle-exceptions-in-spring-boot-a-guide-to-clean-code-principles-e8a9d56cafe8

  @ExceptionHandler(DuplicateReviewException.class)
  public ResponseEntity<String> handleDuplicateReviewException(DuplicateReviewException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // HTTP 409
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // HTTP 409
  }

  @ExceptionHandler(InvalidEmailException.class)
  public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // HTTP 400
  }
}
