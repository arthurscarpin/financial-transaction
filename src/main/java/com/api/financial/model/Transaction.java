package com.api.financial.model;

import com.api.financial.dto.RegisterTransactionDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transacao")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private BigDecimal value;

    @Column(name = "data_hora", nullable = false)
    private OffsetDateTime dateTime;

    public Transaction() {
    }

    public Transaction(RegisterTransactionDto dto) {
        this.value = dto.value();
        this.dateTime = dto.dateTime();
    }

    public Transaction(Long id, BigDecimal value, OffsetDateTime dateTime) {
        this.id = id;
        this.value = value;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}
