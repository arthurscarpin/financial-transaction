package com.api.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageDto(
        String message,

        @JsonProperty("dataHora")
        String dateTime) {
}
