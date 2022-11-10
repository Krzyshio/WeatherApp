package com.company;

import com.company.commons.JsonParser;
import com.company.service.LocalisationService;
import com.company.service.WeatherService;
import com.company.view.ViewManager;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        LocalisationService loc = new LocalisationService();

        WeatherService weatherService = new WeatherService("1", "5");
        String jsonString = weatherService.fetchForecast();

        System.out.println(Arrays.toString(JsonParser.parseForecast(jsonString)));
        System.out.println(JsonParser.parseCity(jsonString));

        ViewManager viewManager = new ViewManager();
        viewManager.display();
    }
}