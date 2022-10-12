package com.company.service;

import com.company.requestLabel.Forecast;
import org.apache.http.client.utils.URIBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class WeatherService {
    private static final String PROTOCOL = "https";
    private static final String HOST = "api.openweathermap.org/data/2.5";
    private static final String FORECAST_PATH = "forecast";
    private static final String LATITUDE = "44.34";
    private static final String LONGITUDE = "10.99";
    private static final String UNITS = "metric";

    private final StringBuilder informationString = new StringBuilder();
    private final String appId;

    private HttpURLConnection connection;
    private URL url;
    private int responseCode;

    public WeatherService() {
        this.appId = readAppIdFromProperties();
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

    private void buildForecastUrl() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(PROTOCOL);
        builder.setHost(HOST);
        builder.setPath(FORECAST_PATH);
        builder.addParameter(Forecast.LATITUDE.toString(), LATITUDE);
        builder.addParameter(Forecast.LONGITUDE.toString(), LONGITUDE);
        builder.addParameter(Forecast.UNITS.toString(), UNITS);
        builder.addParameter(Forecast.APP_ID.toString(), appId);

        try {
            url = builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Boolean isConnectionCorrect() throws IOException {
        responseCode = connection.getResponseCode();
        return responseCode == 200;
    }

    private void readResponse() throws IOException {
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }
        scanner.close();
    }

    private void connectionErrorHandler() {
        throw new RuntimeException("HttpResponseCode: " + responseCode);
    }

    public String fetchForecast() {

        buildForecastUrl();

        if (url != null) {
            try {
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();

                if (isConnectionCorrect()) {
                    readResponse();
                } else {
                    connectionErrorHandler();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            return "{}";
        }
        return informationString.toString();
    }
}
