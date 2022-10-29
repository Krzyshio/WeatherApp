package com.company.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SendGetRequestService {

    private SendGetRequestService() {

    }

    private static Boolean isConnectionCorrect(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        return responseCode == 200;
    }

    private static String readResponse(URL url) throws IOException {
        StringBuilder responseStringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            responseStringBuilder.append(scanner.nextLine());
        }
        scanner.close();
        return responseStringBuilder.toString();
    }

    public static String sendGetRequestAndGetResponse(URL url) {
        String response = null;
        HttpURLConnection connection;

        if (url != null) {
            try {
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();

                if (Boolean.TRUE.equals(isConnectionCorrect(connection))) {
                    response = readResponse(url);
                } else {
                    throw new ConnectionErrorException("HttpResponseCode: " + 21);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            return "{}";
        }
        return response;
    }
}
