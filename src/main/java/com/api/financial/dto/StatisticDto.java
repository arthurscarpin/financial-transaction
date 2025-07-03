package com.api.financial.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record StatisticDto(
        Integer count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {
    public StatisticDto(Integer count, double sum, double avg, double min, double max) {
        this(count,
                BigDecimal.valueOf(sum).setScale(3, RoundingMode.HALF_UP),
                BigDecimal.valueOf(avg).setScale(3, RoundingMode.HALF_UP),
                BigDecimal.valueOf(min).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(max).setScale(2, RoundingMode.HALF_UP));
    }

    public static StatisticDto empty() {
        return new StatisticDto(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}