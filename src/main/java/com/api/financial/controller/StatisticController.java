package com.api.financial.controller;

import com.api.financial.dto.StatisticDto;
import com.api.financial.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class StatisticController {

    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);
    @Autowired
    private StatisticsService service;

    @GetMapping("/{seconds}")
    public ResponseEntity<StatisticDto> calculate(@PathVariable int seconds){
        StatisticDto dto = service.calculate(seconds);
        return ResponseEntity.ok(dto);
    }
}
