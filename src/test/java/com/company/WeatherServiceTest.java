package com.company;

import com.company.service.WeatherService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    WeatherService weatherService = new WeatherService("1", "1");

    @Test
    void shouldReturnNotEmptyValue() {
        assertNotNull(weatherService.fetchForecast());
        assertNotEquals("", weatherService.fetchForecast());
    }
}