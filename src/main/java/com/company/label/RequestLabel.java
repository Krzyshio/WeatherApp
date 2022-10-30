package com.company.label;

public enum RequestLabel {
    LATITUDE("lat"),
    LONGITUDE("lon"),
    UNITS("units"),
    APP_ID("appid");

    private final String text;

    RequestLabel(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
