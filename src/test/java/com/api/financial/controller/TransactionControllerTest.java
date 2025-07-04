package com.api.financial.controller;

import com.api.financial.dto.RegisterTransactionDto;
import com.api.financial.service.TransactionService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<RegisterTransactionDto> registerTransactionDtoJson;

    @MockBean
    private TransactionService service;

    @Test
    @DisplayName("Deve retornar código 201 ao registrar transação válida")
    void registerTransaction_CenarioValido() throws Exception {
        var dto = new RegisterTransactionDto(
                new BigDecimal("120.55"),
                OffsetDateTime.parse("2025-07-04T03:53:07.877288400Z")
        );

        var response = mvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerTransactionDtoJson.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(service, times(1)).register(dto);
    }

    @Test
    @DisplayName("Deve retornar código 400 ao registrar transação com dados inválidos")
    void registerTransaction_CenarioInvalido() throws Exception {
        var jsonEsperado = """
        {
          "message": "Transação inválida",
          "dataHora": "2025-07-04T15:30:00Z"
        }
        """;

        var response = mvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEsperado)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        verify(service, never()).register(any());
    }

    @Test
    @DisplayName("Deve retornar código 204 ao remover todas as transações")
    void removeTransactions() throws Exception {
        var response = mvc.perform(delete("/transacao"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).removeAll();
    }
}
