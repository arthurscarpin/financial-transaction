package com.api.financial.controller;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterTransactionDto dto) {
        service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> remove() {
        service.removeAll();
        return ResponseEntity.noContent().build();
    }
}
