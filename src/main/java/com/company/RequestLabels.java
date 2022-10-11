package com.company;

public enum RequestLabels {
    LATITUDE("lat"),
    LONGITUDE("lon"),
    UNITS("units"),
    APP_ID("appid")
    ;

    private final String text;

    RequestLabels(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
