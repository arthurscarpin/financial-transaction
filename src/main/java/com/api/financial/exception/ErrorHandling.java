package com.api.financial.exception;

import com.api.financial.dto.MessageDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageDto> handleError400(HttpMessageNotReadableException ex) {
        String message = "O corpo da requisição está fora de formatação.";
        MessageDto dto = new MessageDto(message, OffsetDateTime.now().toString());
        return ResponseEntity.status(400).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Erro de validação nos dados enviados.");
        MessageDto dto = new MessageDto(errorMessage, OffsetDateTime.now().toString());
        return ResponseEntity.status(422).body(dto);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<MessageDto> handleCustomError422(CustomException error) {
        MessageDto dto = new MessageDto(error.getMessage(), OffsetDateTime.now().toString());
        return ResponseEntity.status(422).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> handleGenericError500(Exception ex) {
        String message = "Erro: " + ex.getMessage();
        MessageDto dto = new MessageDto(message, OffsetDateTime.now().toString());
        return ResponseEntity.status(500).body(dto);
    }
}