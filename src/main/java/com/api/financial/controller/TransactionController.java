package com.api.financial.controller;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.service.TransactionService;
import jakarta.validation.Valid;
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
    private TransactionService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid RegisterTransactionDto dto) {
        service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
