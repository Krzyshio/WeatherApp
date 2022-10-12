package com.company;

import com.company.entity.Forecast;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class JsonParserTest {

    @Test
    void parseNullForecast() {
        Forecast[] forecasts = JsonParser.parseForecast(null);
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 0);
    }

    @Test
    void parseEmptyForecast() {
        Forecast[] forecasts = JsonParser.parseForecast("");
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 0);
    }

    @Test
    void parseForecastResponseWithoutListKey() {
        Forecast[] forecasts = JsonParser.parseForecast("{\"name\": \"Krzysztof Grabowski\"}");
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 0);
    }

    @Test
    void parseExampleCorrectResponse() throws IOException {

        InputStream inStream = new FileInputStream("src/test/testResources/example_forecast_response.json");
        String response = IOUtils.toString(inStream, StandardCharsets.UTF_8.name());

        Forecast[] forecasts = JsonParser.parseForecast(response);
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 40);
    }
}