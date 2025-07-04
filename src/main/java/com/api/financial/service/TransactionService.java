package com.api.financial.service;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    private TransactionRepository repository;

    public void register(RegisterTransactionDto dto) {
        log.info("Registrando nova transação: valor={}, dataHora={}", dto.value(), dto.dateTime());
        repository.save(new Transaction(dto));
        log.info("Transação registrada com sucesso.");
    }

    public void removeAll() {
        log.warn("Iniciando remoção de todas as transações.");
        repository.deleteAll();
        log.warn("Todas as transações foram removidas com sucesso.");
    }
}
