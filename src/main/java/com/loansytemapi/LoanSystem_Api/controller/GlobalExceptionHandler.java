package com.loansytemapi.LoanSystem_Api.controller;

import com.loansytemapi.LoanSystem_Api.dto.ErrorResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundExeption(NotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
