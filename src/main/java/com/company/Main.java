package com.company;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();

        String jsonString = weatherService.fetchForecast();
        JSONObject obj = new JSONObject(jsonString);
        String pageName = obj.getJSONObject("city").toString();
        City city = JSON.parseObject(pageName, City.class);

        System.out.println(city);
    }
}