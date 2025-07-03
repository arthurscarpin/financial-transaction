package com.api.financial.service;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public void register(RegisterTransactionDto dto) {
        repository.save(new Transaction(dto));
    }

    public void removeAll() {
        repository.deleteAll();
    }
}
