package com.api.financial.service;

import com.api.financial.dto.StatisticDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private TransactionRepository repository;

    public StatisticDto calculate(int seconds) {
        OffsetDateTime limit = OffsetDateTime.now().minusSeconds(seconds);
        List<Transaction> values = repository.findByDateTimeAfter(limit);

        DoubleSummaryStatistics statistics = values.stream()
                .mapToDouble(t -> t.getValue().doubleValue())
                .summaryStatistics();

        if (statistics.getCount() == 0) {
            return StatisticDto.empty();
        }

        return new StatisticDto(
                (int) statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                statistics.getMin(),
                statistics.getMax()
        );
    }
}