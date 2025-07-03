package com.api.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RegisterTransactionDto(
        @JsonProperty("valor")
        @NotNull(message = "O campo valor é obrigatório")
        @DecimalMin(value = "0.00", inclusive = true, message = "O valor da transação deve ser igual ou maior que zero")
        BigDecimal value,

        @JsonProperty("dataHora")
        @NotNull(message = "O campo dataHora é obrigatório")
        @PastOrPresent(message = "A data e o horário não podem ser futuros")
        OffsetDateTime dateTime) {
}
