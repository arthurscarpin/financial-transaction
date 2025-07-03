package com.api.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RegisterTransactionDto(
        @JsonProperty("valor")
        BigDecimal value,
        @JsonProperty("dataHora")
        OffsetDateTime dateTime) {
}
