package com.company.service;

import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class LocalisationService {
    private static final String PROTOCOL = "https";
    private static final String HOST = "geolocation-db.com";
    private static final String FORECAST_PATH = "jsonp";

    private final StringBuilder informationString = new StringBuilder();

    private HttpURLConnection connection;
    private URL url;
    private int responseCode;

    private void buildForecastUrl() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(PROTOCOL);
        builder.setHost(HOST);
        builder.setPath(FORECAST_PATH);

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

    public String fetchDefaultCoordinates() {

        buildForecastUrl();

        if (url != null) {
            try {
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();

                if (Boolean.TRUE.equals(isConnectionCorrect())) {
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
