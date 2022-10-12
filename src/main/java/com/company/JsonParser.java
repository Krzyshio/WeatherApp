package com.company;

import com.company.entity.Forecast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser {
    private final static String FORECAST_LABEL = "list";

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
}
