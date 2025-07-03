package com.api.financial.controller;

import com.api.financial.dto.StatisticDto;
import com.api.financial.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class StatisticController {

    @Autowired
    private StatisticsService service;

    @GetMapping
    public ResponseEntity<StatisticDto> calculate(){
        StatisticDto dto = service.calculate(60);
        return ResponseEntity.ok(dto);
    }
}
