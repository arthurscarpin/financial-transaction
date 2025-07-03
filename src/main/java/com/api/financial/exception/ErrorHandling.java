package com.api.financial.infra.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler({IllegalStateException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Void> handleError422() {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, Exception.class})
    public ResponseEntity<Void> handleError400() {
        return ResponseEntity.badRequest().build();
    }
}
