package com.company.view;

import com.company.commons.JsonParser;
import com.company.entity.Forecast;
import com.company.service.WeatherService;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.commons.CustomBoxDrawHelper.drawCustomBox;

public class WeatherOverview extends ViewModel {
    private static final Integer DEFAULT_BOX_HEIGHT = 15;
    private static final Integer DEFAULT_SECTION_WIDTH = 50;
    private static final Integer NUMBER_OF_DISPLAYED_FORECASTS = 3;
    private static final Integer FORECASTS_SECTION_PADDING = 10;
    private static final Integer DEFAULT_FORECASTS_SPACING = 20;

    private final static WeatherService weatherService = new WeatherService();

    private static final Forecast[] forecasts = Arrays.stream(JsonParser.parseForecast(weatherService.fetchForecast()))
            .limit(NUMBER_OF_DISPLAYED_FORECASTS)
            .toArray(Forecast[]::new);

    private static void drawWeatherDetails() {
        AtomicInteger i = new AtomicInteger();

        Arrays.stream(forecasts).forEach(forecast -> {
            i.getAndIncrement();
            System.out.println("a");
            drawWeatherDetailsBox(new TerminalPosition(0, (i.get() * DEFAULT_FORECASTS_SPACING) + FORECASTS_SECTION_PADDING),
                    forecast);
        });
    }

    private static void drawWeatherDetailsBox(TerminalPosition terminalPosition, Forecast forecast) {

        drawCustomBox(new TerminalPosition(0, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT, DEFAULT_SECTION_WIDTH);
        drawCustomBox(new TerminalPosition(50, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT, DEFAULT_SECTION_WIDTH);
        drawCustomBox(new TerminalPosition(100, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT, DEFAULT_SECTION_WIDTH);
        drawCustomBox(new TerminalPosition(150, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT, DEFAULT_SECTION_WIDTH);

        drawCustomBox(new TerminalPosition(0, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT / 5, DEFAULT_SECTION_WIDTH);
        drawCustomBox(new TerminalPosition(50, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT / 5, DEFAULT_SECTION_WIDTH);
        drawCustomBox(new TerminalPosition(100, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT / 5, DEFAULT_SECTION_WIDTH);
        drawCustomBox(new TerminalPosition(150, terminalPosition.getRow()), DEFAULT_BOX_HEIGHT / 5, DEFAULT_SECTION_WIDTH);

        drawCustomBox(new TerminalPosition(87, terminalPosition.getRow() - 2), DEFAULT_BOX_HEIGHT / 5, 26);
        ViewManager.getTextGraphics().fillRectangle(new TerminalPosition(88, terminalPosition.getRow() - 1),
                new TerminalSize(25, 2), ' ');
    }

    @Override
    public void display() throws IOException {
        ViewManager.getTerminal().clearScreen();
        drawWeatherDetails();
        ViewManager.getTerminal().flush();
    }
}
