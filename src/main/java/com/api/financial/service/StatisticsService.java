package com.api.financial.service;

import com.api.financial.dto.StatisticDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Slf4j
@Service
public class StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);
    @Autowired
    private TransactionRepository repository;

    public StatisticDto calculate(int seconds) {
        OffsetDateTime limit = OffsetDateTime.now().minusSeconds(seconds);
        log.info("Iniciando cálculo estatístico para os últimos {} segundos desde {}.", seconds, limit);

        List<Transaction> values = repository.findByDateTimeAfter(limit);
        log.debug("Foram recuperadas {} transações para cálculo estatístico.", values.size());

        DoubleSummaryStatistics statistics = values.stream()
                .mapToDouble(t -> t.getValue().doubleValue())
                .summaryStatistics();

        if (statistics.getCount() == 0) {
            log.info("Nenhuma transação encontrada no intervalo.");
            return StatisticDto.empty();
        }

        StatisticDto dto = new StatisticDto(
                (int) statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                statistics.getMin(),
                statistics.getMax()
        );
        log.info("Estatística gerada: count={}, sum={}, avg={}, min={}, max={}",
                dto.count(), dto.sum(), dto.avg(), dto.min(), dto.max());
        return dto;
    }
}