package com.company;

import com.company.service.WeatherService;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();

        String jsonString = weatherService.fetchForecast();

        System.out.println(Arrays.toString(JsonParser.parseForecast(jsonString)));
    }
}