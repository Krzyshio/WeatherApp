package com.company.service;

import com.company.label.RequestLabel;
import org.apache.http.client.utils.URIBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class WeatherService {
    private static final String PROTOCOL = "https";
    private static final String HOST = "api.openweathermap.org/data/2.5";
    private static final String FORECAST_PATH = "forecast";
    private static final String UNITS = "metric";

    private final String latitude;
    private final String longitude;
    private final String appId;
    private URL url;

    public WeatherService(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.appId = readAppIdFromProperties();
        if (appId == null || appId.isEmpty()) {
            throw new EmptyApiKeyException();
        }
    }

    private String readAppIdFromProperties() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources/local.properties")) {

            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop.getProperty("apiKey");
    }

    private URL buildForecastUrl() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(PROTOCOL);
        builder.setHost(HOST);
        builder.setPath(FORECAST_PATH);
        builder.addParameter(RequestLabel.LATITUDE.toString(), latitude);
        builder.addParameter(RequestLabel.LONGITUDE.toString(), longitude);
        builder.addParameter(RequestLabel.UNITS.toString(), UNITS);
        builder.addParameter(RequestLabel.APP_ID.toString(), appId);

        try {
            url = builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }

        return url;
    }

    public String fetchForecast() {
        URL forecastUrl = buildForecastUrl();
        return SendGetRequestService.sendGetRequestAndGetResponse(forecastUrl);
    }
}
