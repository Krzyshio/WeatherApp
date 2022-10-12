package com.company;

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
        Assertions.assertEquals(forecasts.length, 0);
    }

    @Test
    void parseEmptyForecast() {
        Forecast[] forecasts = JsonParser.parseForecast("");
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 0);
    }

    @Test
    void parseForecastResponseFromCorrectJsonButWithoutListKey() {
        Forecast[] forecasts = JsonParser.parseForecast("{\"name\": \"Krzysztof Grabowski\"}");
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 0);
    }

    @Test
    void parseForecastFromExampleCorrectResponse() {

        Forecast[] forecasts = JsonParser.parseForecast(response);
        Assertions.assertNotNull(forecasts);
        Assertions.assertEquals(forecasts.length, 40);
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
        Assertions.assertEquals(city.getId(), 3163858);
        Assertions.assertEquals(city.getName(), "Zocca");
        Assertions.assertEquals(city.getCoord(), new Coordinate(44.34,10.99));
        Assertions.assertEquals(city.getCountry(), "IT");
        Assertions.assertEquals(city.getPopulation(), 4593);
        Assertions.assertEquals(city.getTimezone(), 7200);
        Assertions.assertEquals(city.getSunrise(), 1665552403);
        Assertions.assertEquals(city.getSunset(), 1665592686);
    }
}