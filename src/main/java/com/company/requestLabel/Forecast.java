package com.company.requestLabel;

public enum Forecast {
    LATITUDE("lat"),
    LONGITUDE("lon"),
    UNITS("units"),
    APP_ID("appid")
    ;

    private final String text;

    Forecast(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
