package com.company.commons;

import com.company.entity.City;
import com.company.entity.Coordinate;
import com.company.entity.Forecast;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class JsonParserTest {
    private final InputStream inStream = new FileInputStream("src/test/testResources/example_forecast_response.json");
    private final String response = IOUtils.toString(inStream, StandardCharsets.UTF_8.name());

    JsonParserTest() throws IOException {
    }

    @Test
    void parseNullForecast() {
        Forecast[] forecasts = JsonParser.parseForecast(null);
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(0, forecasts.length);
    }

    @Test
    void parseEmptyForecast() {
        Forecast[] forecasts = JsonParser.parseForecast("");
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(0, forecasts.length);
    }

    @Test
    void parseForecastResponseFromCorrectJsonButWithoutListKey() {
        Forecast[] forecasts = JsonParser.parseForecast("{\"name\": \"Krzysztof Grabowski\"}");
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(0, forecasts.length);
    }

    @Test
    void parseForecastFromExampleCorrectResponse() {

        Forecast[] forecasts = JsonParser.parseForecast(response);
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(40, forecasts.length);
        Assertions.assertNotNull(forecasts[0].getDt());
        Assertions.assertNotNull(forecasts[0].getMain());
        Assertions.assertNotNull(forecasts[0].getClouds());
        Assertions.assertNotNull(forecasts[0].getPop());
        Assertions.assertNotNull(forecasts[0].getDt_txt());
        Assertions.assertNotNull(forecasts[0].getSys());
        Assertions.assertNotNull(forecasts[0].getVisibility());
        Assertions.assertNotNull(forecasts[0].getWeather());
        Assertions.assertNotNull(forecasts[0].getWind());
    }

    @Test
    void parseNullCity() {
        City city = JsonParser.parseCity(null);
        Assertions.assertNotNull(city);
    }

    @Test
    void parseEmptyCity() {
        City city = JsonParser.parseCity("");
        Assertions.assertNotNull(city);
    }

    @Test
    void parseResponseFromCorrectJsonButWithoutCityKey() {
        City city = JsonParser.parseCity("{\"name\": \"Krzysztof Grabowski\"}");
        Assertions.assertNotNull(city);
    }

    @Test
    void parseCityFromExampleCorrectResponse() {

        City city = JsonParser.parseCity(response);
        Assertions.assertNotNull(city);
        Assertions.assertEquals(3163858, city.getId());
        Assertions.assertEquals("Zocca", city.getName());
        Assertions.assertEquals(new Coordinate(44.34, 10.99), city.getCoord());
        Assertions.assertEquals("IT", city.getCountry());
        Assertions.assertEquals(4593, city.getPopulation());
        Assertions.assertEquals(7200, city.getTimezone());
        Assertions.assertEquals(1665552403, city.getSunrise());
        Assertions.assertEquals(1665592686, city.getSunset());
    }
}