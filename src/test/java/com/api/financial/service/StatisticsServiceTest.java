package com.api.financial.service;

import com.api.financial.dto.StatisticDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService service;

    @Mock
    private TransactionRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve calcular estatísticas corretamente para transações existentes")
    void testCalculate_WithTransactions() {
        var now = OffsetDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction(1L, new BigDecimal("100.0"), now.minusSeconds(5)),
                new Transaction(2L, new BigDecimal("200.0"), now.minusSeconds(3)),
                new Transaction(3L, new BigDecimal("300.0"), now.minusSeconds(1))
        );
        when(repository.findByDateTimeAfter(any())).thenReturn(transactions);

        StatisticDto result = service.calculate(10);
        assertThat(result.sum()).isCloseTo(BigDecimal.valueOf(600.0), offset(BigDecimal.valueOf(0.001)));
        assertThat(result.avg()).isCloseTo(BigDecimal.valueOf(200.0), offset(BigDecimal.valueOf(0.001)));
        assertThat(result.min()).isCloseTo(BigDecimal.valueOf(100.0), offset(BigDecimal.valueOf(0.001)));
        assertThat(result.max()).isCloseTo(BigDecimal.valueOf(300.0), offset(BigDecimal.valueOf(0.001)));

        verify(repository, times(1)).findByDateTimeAfter(any());
    }

    @Test
    @DisplayName("Deve retornar estatística vazia quando não houver transações")
    void testCalculate_NoTransactions() {
        when(repository.findByDateTimeAfter(any())).thenReturn(List.of());

        StatisticDto result = service.calculate(10);
        assertThat(result.count()).isZero();
        assertThat(result.sum()).isZero();
        assertThat(result.avg()).isZero();
        assertThat(result.min()).isZero();
        assertThat(result.max()).isZero();

        verify(repository, times(1)).findByDateTimeAfter(any());
    }

    @Test
    @DisplayName("Deve chamar repository com data limite correta")
    void testCalculate_VerifyDateTimeParameter() {
        ArgumentCaptor<OffsetDateTime> captor = ArgumentCaptor.forClass(OffsetDateTime.class);
        when(repository.findByDateTimeAfter(any())).thenReturn(List.of());

        service.calculate(30);
        verify(repository).findByDateTimeAfter(captor.capture());
        OffsetDateTime captured = captor.getValue();
        OffsetDateTime expectedLimit = OffsetDateTime.now().minusSeconds(30);

        assertThat(captured).isBeforeOrEqualTo(OffsetDateTime.now());
        assertThat(captured).isAfter(expectedLimit.minusSeconds(2));
    }
}
