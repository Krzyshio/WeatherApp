package com.company;

public class Main {

    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();

        System.out.println(weatherService.fetchForecast());
    }
}