package com.company.service;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LocalisationService {
    private static final String PROTOCOL = "https";
    private static final String HOST = "geolocation-db.com";
    private static final String FORECAST_PATH = "jsonp";
    private URL url;

    private URL buildLocalisationUrl() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(PROTOCOL);
        builder.setHost(HOST);
        builder.setPath(FORECAST_PATH);

        try {
            url = builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String fetchDefaultCoordinates() {
        URL localisationRequestURL = buildLocalisationUrl();

        return SendGetRequestService.sendGetRequestAndGetResponse(localisationRequestURL);
    }
}
