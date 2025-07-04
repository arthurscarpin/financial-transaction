package com.api.financial.repository;

import com.api.financial.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("Deve retornar transações após data limite")
    void testFindByDateTimeAfter() {
        var now = OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);
        var past = now.minusMinutes(10);
        var future = now.plusMinutes(10);

        var transaction1 = new Transaction(null, new BigDecimal("100.00"), past);
        var transaction2 = new Transaction(null, new BigDecimal("200.00"), now);
        var transaction3 = new Transaction(null, new BigDecimal("300.00"), future);

        repository.save(transaction1);
        repository.save(transaction2);
        repository.save(transaction3);

        List<Transaction> results = repository.findByDateTimeAfter(now);

        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getValue()).isEqualByComparingTo("300.00");
        assertThat(results.get(0).getDateTime()).isEqualTo(future);
    }

    @Test
    @DisplayName("Deve retornar lista vazia se nenhuma transação for encontrada após data limite")
    void testFindByDateTimeAfter_NoResults() {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var past = now.minusDays(1);

        var transaction = new Transaction(null, new BigDecimal("150.00"), past);
        repository.save(transaction);
        List<Transaction> results = repository.findByDateTimeAfter(now);

        assertThat(results).isEmpty();
    }
}
