package com.api.financial.infra.exception;

import com.api.financial.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandling.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageDto> handleError400(HttpMessageNotReadableException error) {
        log.warn("Requisição malformada: {}", error.getMessage());
        String message = "O corpo da requisição está fora de formatação.";
        MessageDto dto = new MessageDto(message, OffsetDateTime.now().toString());
        return ResponseEntity.status(400).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> handleValidationError(MethodArgumentNotValidException error) {
        String message = error.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Erro de validação nos dados enviados.");
        log.warn("Erro de validação: {}", message);
        MessageDto dto = new MessageDto(message, OffsetDateTime.now().toString());
        return ResponseEntity.status(422).body(dto);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<MessageDto> handleCustomError422(CustomException error) {
        log.error("Exceção de negócio: {}", error.getMessage(), error);
        MessageDto dto = new MessageDto(error.getMessage(), OffsetDateTime.now().toString());
        return ResponseEntity.status(422).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> handleGenericError500(Exception error) {
        log.error("Erro genérico (Não mapeado): {}", error.getMessage(), error);
        String message = "Erro: " + error.getMessage();
        MessageDto dto = new MessageDto(message, OffsetDateTime.now().toString());
        return ResponseEntity.status(500).body(dto);
    }
}