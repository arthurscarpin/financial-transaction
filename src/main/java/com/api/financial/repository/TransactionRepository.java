package com.api.financial.repository;

import com.api.financial.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByValueAndDateTime(BigDecimal value, OffsetDateTime dateTime);
}
