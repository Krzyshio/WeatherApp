package com.company.commons;

import com.company.entity.City;
import com.company.entity.Forecast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser {
    private final static String FORECAST_LABEL = "list";
    private final static String CITY_LABEL = "city";

    public static Forecast[] parseForecast(String response) {
        Forecast[] forecasts;
        try {
            JSONObject obj = new JSONObject(response);

            String list = obj.getJSONArray(FORECAST_LABEL).toString();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            forecasts = gson.fromJson(list, Forecast[].class);

        } catch (JSONException | JsonSyntaxException | NullPointerException e ) {
            System.out.println("getMessage(): " + e.getMessage());
            e.printStackTrace();
            forecasts = new Forecast[0];
        }

        return forecasts;
    }

    public static City parseCity(String response) {
        City city;
        try {
            JSONObject obj = new JSONObject(response);

            String cityResponse = obj.getJSONObject(CITY_LABEL).toString();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            city = gson.fromJson(cityResponse, City.class);

        } catch (JSONException | JsonSyntaxException | NullPointerException e ) {
            System.out.println("getMessage(): " + e.getMessage());
            e.printStackTrace();
            city = new City();
        }

        return city;
    }
}
