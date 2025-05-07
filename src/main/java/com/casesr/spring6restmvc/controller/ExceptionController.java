package com.casesr.spring6restmvc.controller;

import com.casesr.spring6restmvc.exception.NotFoundException;
import com.casesr.spring6restmvc.model.Beer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Racquel.Cases
 */
@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Beer> handleNotFound() {
    return ResponseEntity.notFound().build();
  }
}
