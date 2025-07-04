package com.api.financial.service;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve registrar nova transação corretamente")
    void testRegisterTransaction() {
        RegisterTransactionDto dto = mock(RegisterTransactionDto.class);
        when(dto.value()).thenReturn(new java.math.BigDecimal("123.45"));
        when(dto.dateTime()).thenReturn(java.time.OffsetDateTime.now());

        service.register(dto);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository, times(1)).save(captor.capture());

        Transaction savedTransaction = captor.getValue();
        assertThat(savedTransaction.getValue()).isEqualByComparingTo(dto.value());
        assertThat(savedTransaction.getDateTime()).isEqualTo(dto.dateTime());
    }

    @Test
    @DisplayName("Deve remover todas as transações")
    void testRemoveAll() {
        service.removeAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar transação quando repositório falha")
    void testRegisterThrowsExceptionOnRepositoryFailure() {
        RegisterTransactionDto dto = mock(RegisterTransactionDto.class);
        when(dto.value()).thenReturn(new BigDecimal("100.00"));
        when(dto.dateTime()).thenReturn(OffsetDateTime.now());

        doThrow(new RuntimeException("DB failure")).when(repository).save(any(Transaction.class));

        assertThatThrownBy(() -> service.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB failure");
    }

    @Test
    @DisplayName("Deve propagar exceção se falha ocorrer ao remover todas as transações")
    void testRemoveAllThrowsException() {
        doThrow(new RuntimeException("DB failure on deleteAll")).when(repository).deleteAll();

        assertThatThrownBy(() -> service.removeAll())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB failure on deleteAll");
    }
}
