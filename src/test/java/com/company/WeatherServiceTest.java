package com.company;

import com.company.service.WeatherService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherServiceTest {

    static WeatherService weatherService = new WeatherService("1", "1");

    @Test
    void shouldReturnNotEmptyValue() {
        assertNotNull(weatherService.fetchForecast());
        assertNotEquals("", weatherService.fetchForecast());
    }
}