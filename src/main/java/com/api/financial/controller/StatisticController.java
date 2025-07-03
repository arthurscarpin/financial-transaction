package com.api.financial.controller;

import com.api.financial.dto.StatisticDto;
import com.api.financial.model.Transaction;
import com.api.financial.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@RestController
@RequestMapping("/estatistica")
public class StatisticController {

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public ResponseEntity<StatisticDto> calculate(){
        OffsetDateTime limit = OffsetDateTime.now().minusSeconds(60);
        List<Transaction> values = repository.findByDateTimeAfter(limit);

        DoubleSummaryStatistics statistics = values.stream()
                .mapToDouble(t -> t.getValue().doubleValue())
                .summaryStatistics();

        if (statistics.getCount() == 0) {
            return ResponseEntity.ok(StatisticDto.empty());
        }

        return ResponseEntity.ok(new StatisticDto(
                (int) statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                statistics.getMin(),
                statistics.getMax()
        ));
    }
}
