package com.api.financial.controller;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid RegisterTransactionDto dto) {
        try {
            boolean alreadyRegistered = repository.existsByValueAndDateTime(dto.value(), dto.dateTime());
            if (alreadyRegistered) {
                throw new ValidationException();
            }
            repository.save(new Transaction(dto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ValidationException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
