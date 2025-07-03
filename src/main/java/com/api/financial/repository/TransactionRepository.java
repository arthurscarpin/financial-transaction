package com.api.financial.repository;

import com.api.financial.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateTimeAfter(OffsetDateTime limit);
}
