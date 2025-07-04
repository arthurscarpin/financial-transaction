package com.api.financial.controller;

import com.api.financial.dto.StatisticDto;
import com.api.financial.service.StatisticsService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class StatisticControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<StatisticDto> statisticDtoJson;

    @MockBean
    private StatisticsService service;

    @Test
    @DisplayName("Deve retornar o código HTTP 200 e corpo correto para cálculo de estatística")
    void testCalculate_ReturnsStatisticDto() throws Exception {
        int seconds = 60;
        StatisticDto dto = new StatisticDto(5, 100.0, 20.0, 10.0, 30.0);
        when(service.calculate(seconds)).thenReturn(dto);

        var response = mvc.perform(
                        get("/estatistica/{seconds}", seconds)
                                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(statisticDtoJson.write(dto).getJson());
        verify(service, times(1)).calculate(seconds);
    }

    @Test
    @DisplayName("Deve retornar código HTTP 200 e estatística vazia quando não houver dados")
    void testCalculate_ReturnsEmptyStatisticDto() throws Exception {
        int seconds = 60;
        StatisticDto emptyDto = StatisticDto.empty();
        when(service.calculate(seconds)).thenReturn(emptyDto);

        var response = mvc.perform(
                        get("/estatistica/{seconds}", seconds)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(statisticDtoJson.write(emptyDto).getJson());
        verify(service, times(1)).calculate(seconds);
    }

    @Test
    @DisplayName("Deve retornar código HTTP 500 quando serviço lançar exceção")
    void testCalculate_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        int seconds = 60;
        when(service.calculate(seconds)).thenThrow(new RuntimeException("Erro inesperado"));

        var response = mvc.perform(
                        get("/estatistica/{seconds}", seconds)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        verify(service, times(1)).calculate(seconds);
    }
}