package com.api.financial.controller;

import com.api.financial.dto.StatisticDto;
import com.api.financial.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class StatisticController {

    @Autowired
    private StatisticsService service;

    @GetMapping("/{seconds}")
    public ResponseEntity<StatisticDto> calculate(@PathVariable int seconds){
        StatisticDto dto = service.calculate(seconds);
        return ResponseEntity.ok(dto);
    }
}
