package com.company.view;

import com.company.commons.JsonParser;
import com.company.entity.Forecast;
import com.company.service.WeatherService;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.commons.CustomBoxDrawHelper.drawCustomBox;
import static com.company.commons.Icon.drawIcon;

public class WeatherOverview extends ViewModel {
    private static final Integer DEFAULT_BOX_HEIGHT = 15;
    private static final Integer DEFAULT_SECTION_WIDTH = 50;
    private static final Integer FORECASTS_IN_ROW = 4;
    private static final Integer FORECASTS_ROWS = 3;
    private static final Integer NUMBER_OF_DISPLAYED_FORECASTS = FORECASTS_IN_ROW * FORECASTS_ROWS;
    private static final Integer FORECASTS_SECTION_PADDING = 10;
    private static final Integer DEFAULT_FORECASTS_SPACING = 20;

    private final static WeatherService weatherService = new WeatherService();

    private static final Forecast[] forecasts = Arrays.stream(JsonParser.parseForecast(weatherService.fetchForecast()))
            .limit(NUMBER_OF_DISPLAYED_FORECASTS)
            .toArray(Forecast[]::new);

    private static final Forecast[][] groupedForecasts = splitForecastsInRows();

    private static Forecast[][] splitForecastsInRows() {
        Forecast[][] groupedForecasts = new Forecast[FORECASTS_ROWS][FORECASTS_IN_ROW];
        for (int i = 0; i < FORECASTS_ROWS; i++)
            System.arraycopy(forecasts, (i * FORECASTS_IN_ROW), groupedForecasts[i], 0, FORECASTS_IN_ROW);
        return groupedForecasts;
    }

    private static void drawWeatherDetails() {
        AtomicInteger i = new AtomicInteger();

        Arrays.stream(groupedForecasts).forEach(forecast -> {
            i.getAndIncrement();
            TerminalPosition terminalPosition = new TerminalPosition(0, (i.get() * DEFAULT_FORECASTS_SPACING) + FORECASTS_SECTION_PADDING);
            drawWeatherDetailsRow(terminalPosition,
                    forecast);
            drawRowOverviewLabel(terminalPosition, i.get());
        });
    }

    private static void drawWeatherDetailsRow(TerminalPosition terminalPosition, Forecast[] forecasts) {
        AtomicInteger i = new AtomicInteger();

        Arrays.stream(forecasts).forEach(forecast -> {
            drawWeatherDetailsContainer(new TerminalPosition(terminalPosition.getColumn() + (i.get() * DEFAULT_SECTION_WIDTH),
                    terminalPosition.getRow()), forecast);
            i.getAndIncrement();
        });

    }

    private static void drawRowOverviewLabel(TerminalPosition terminalPosition, Integer hours) {
        drawCustomBox(new TerminalPosition(87, terminalPosition.getRow() - 2), DEFAULT_BOX_HEIGHT / 5, 26);
        ViewManager.getTextGraphics().fillRectangle(new TerminalPosition(88, terminalPosition.getRow() - 1),
                new TerminalSize(25, 2), ' ');
        ViewManager.getTextGraphics().putString(94, terminalPosition.getRow(), "Next " + hours * 12 + " Hours");
    }

    private static Date convertDtToDate(Integer dt) {
        return new Date(Long.valueOf(dt) * 1000);
    }

    private static void drawWeatherDescription(TerminalPosition terminalPosition, Forecast forecast) {
        ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow(), StringUtils.capitalize(forecast.getWeather().get(0).getDescription()));
    }

    private static void drawTemp(TerminalPosition terminalPosition, Forecast forecast) {
        ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow(),"Temp: " + forecast.getMain().getTemp().toString() + "°C");
    }

    private static void drawPressure(TerminalPosition terminalPosition, Forecast forecast) {
        ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow(),"Pressure: " + forecast.getMain().getPressure().toString() + "hPa");
    }

    private static void drawHumidity(TerminalPosition terminalPosition, Forecast forecast) {
        ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow(),"Humidity: " + forecast.getMain().getHumidity().toString() + "%");
    }


    private static void drawWeatherDetailsContainer(TerminalPosition terminalPosition, Forecast forecast) {

        drawCustomBox(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()), DEFAULT_BOX_HEIGHT, DEFAULT_SECTION_WIDTH);
        ViewManager.getTextGraphics().putCSIStyledString(new TerminalPosition(terminalPosition.getColumn() + 12,
                terminalPosition.getRow() + 2), convertDtToDate(forecast.getDt()).toString());

        drawCustomBox(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()), DEFAULT_BOX_HEIGHT / 5, DEFAULT_SECTION_WIDTH);
        drawIcon(new TerminalPosition(terminalPosition.getColumn() + 2, terminalPosition.getRow() + 6));
        drawWeatherDescription(new TerminalPosition(terminalPosition.getColumn() + 14, terminalPosition.getRow() + 6), forecast);
        drawTemp(new TerminalPosition(terminalPosition.getColumn() + 14, terminalPosition.getRow() + 7), forecast);
        drawPressure(new TerminalPosition(terminalPosition.getColumn() + 14, terminalPosition.getRow() + 8), forecast);
        drawHumidity(new TerminalPosition(terminalPosition.getColumn() + 14, terminalPosition.getRow() + 9), forecast);
    }


    @Override
    public void display() throws IOException {
        ViewManager.getTerminal().clearScreen();
        drawWeatherDetails();
        ViewManager.getTerminal().flush();
    }
}
