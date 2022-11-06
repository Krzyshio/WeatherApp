package com.company.view;

import com.company.button.Button;
import com.company.commons.JsonParser;
import com.company.entity.Forecast;
import com.company.service.WeatherService;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.mitchtalmadge.asciidata.graph.ASCIIGraph;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.commons.DrawingHelper.drawAsciiArt;
import static com.company.commons.DrawingHelper.drawTerminalOverlay;
import static com.company.view.ViewManager.getAppMainColour;

public class WeatherChartsView implements View {
    private static final Integer DEFAULT_HEADER_POSITION_Y = 3;
    private static final WeatherService WEATHER_SERVICE = new WeatherService("50", "40");
    private static final Integer CHART_WIDTH = 4;
    private static final Integer CHART_HEIGHT = 50;
    private static final Integer CHART_POSITION_X = 10;
    private static final Integer CHART_POSITION_Y = 40;
    private static KeyStroke actualKey;
    private static final Forecast[] forecasts = Arrays.stream(JsonParser.parseForecast(WEATHER_SERVICE.fetchForecast()))
            .toArray(Forecast[]::new);

    private static final String[] WEATHER_CHARTS_HEADER = {
            " ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄       ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄ ",
            "▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌",
            "▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌     ▐░█▀▀▀▀▀▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀ ",
            "▐░▌       ▐░▌▐░▌          ▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌          ▐░▌       ▐░▌     ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌          ",
            "▐░▌   ▄   ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌     ▐░▌          ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄ ",
            "▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░░░░░░░░░░░▌",
            "▐░▌ ▐░▌░▌ ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌     ▐░▌     ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀█░█▀▀      ▐░▌          ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀█░█▀▀      ▐░▌      ▀▀▀▀▀▀▀▀▀█░▌",
            "▐░▌▐░▌ ▐░▌▐░▌▐░▌          ▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌          ▐░▌     ▐░▌       ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌     ▐░▌       ▐░▌               ▐░▌",
            "▐░▌░▌   ▐░▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌      ▐░▌      ▐░█▄▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌      ▐░▌      ▐░▌      ▄▄▄▄▄▄▄▄▄█░▌",
            "▐░░▌     ▐░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌     ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░░░░░░░░░░░▌",
            " ▀▀       ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀       ▀       ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀         ▀  ▀         ▀       ▀       ▀▀▀▀▀▀▀▀▀▀▀ ",
    };

    private static final String[] TEMPERATURE_OPTION_LABEL = {
            "  _______                    ",
            " |__   __|                   ",
            "    | | ___ _ __ ___  _ __   ",
            "    | |/ _ \\ '_ ` _ \\| '_ \\ ",
            "    | |  __/ | | | | | |_) | ",
            "    |_|\\___|_| |_| |_| .__/ ",
            "                             ",
    };
    private static final String[] HUMIDITY_OPTION_LABEL = {
            "  _    _                 _   ",
            " | |  | |               (_)  ",
            " | |__| |_   _ _ __ ___  _   ",
            " |  __  | | | | '_ ` _ \\| | ",
            " | |  | | |_| | | | | | | |  ",
            " |_|  |_|\\__,_|_| |_| |_|_| ",
            "                             ",
    };
    private static final String[] PRESSURE_OPTION_LABEL = {
            " _____  _____  ______  _____  ",
            " |  __ \\|  __ \\|  ____|/ ____| ",
            " | |__) | |__) | |__  | (___   ",
            " |  ___/|  _  /|  __|  \\___ \\  ",
            " | |    | | \\ \\| |____ ____) | ",
            " |_|    |_|  \\_\\______|_____/  ",
            "                               ",

    };
    private static final double[] temperatures = ArrayUtils.toPrimitive(Arrays.stream(forecasts).map(f -> f.getMain().getTemp()).toArray(Double[]::new));
    private static final double[] humidity = ArrayUtils.toPrimitive(Arrays.stream(forecasts).map(f -> f.getMain().getHumidity()).toArray(Double[]::new));
    private static final double[] pressure = ArrayUtils.toPrimitive(Arrays.stream(forecasts).map(f -> f.getMain().getPressure()).toArray(Double[]::new));
    private static final Map<Integer, double[]> chartsData = new HashMap<>();
    private static final Map<Integer, Button> chartOptions = new HashMap<>();
    private static Integer activeChart = 0;
    private static boolean isChartViewActive = true;

    static {
        chartsData.put(0, temperatures);
        chartsData.put(1, humidity);
        chartsData.put(2, pressure);

        chartOptions.put(0, new Button(TEMPERATURE_OPTION_LABEL));
        chartOptions.put(1, new Button(HUMIDITY_OPTION_LABEL));
        chartOptions.put(2, new Button(PRESSURE_OPTION_LABEL));
    }
    public static void refreshButtonsPositions() {
        AtomicInteger i = new AtomicInteger();

        chartOptions.forEach((key, value) -> {
            value.setTerminalPosition(new TerminalPosition(50 + i.getAndIncrement() * 40, 25));
            chartOptions.put(key, value);
        });
    }

    private static void drawChartOptions() {
        refreshButtonsPositions();
        chartOptions.values().forEach(Button::display);
        chartOptions.get(activeChart).mark();
    }

    private static void manageActiveButtonPosition() {
        if (actualKey == null)
            return;
        if (actualKey.getKeyType() == KeyType.ArrowRight) {
            activeChart = activeChart < chartOptions.size() - 1 ? activeChart + 1 : 0;
        }
        if (actualKey.getKeyType() == KeyType.ArrowLeft) {
            activeChart = activeChart > 0 ? activeChart - 1 : chartOptions.size() - 1;
        }
    }

    private static double[] resizeChart(double[] data) {
        double[] newChart = new double[data.length * CHART_WIDTH];
        AtomicInteger index = new AtomicInteger(0);

        Arrays.stream(data).forEach(value -> {
            for (int i = 0; i < CHART_WIDTH; i++) {
                newChart[index.get() * CHART_WIDTH + i] = value;
            }
            index.getAndIncrement();
        });

        return newChart;
    }

    private static void drawTemperaturesChart() {
        drawAsciiArt(new TerminalPosition(CHART_POSITION_X, CHART_POSITION_Y), ASCIIGraph
                .fromSeries(resizeChart(chartsData.get(activeChart)))
                .withTickFormat(new DecimalFormat("##0.00"))
                .withNumRows(CHART_HEIGHT)
                .plot().split("\n"));
    }

    public static void drawWeatherChartsOverlay() {
        ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());

        ViewManager.getTextGraphics().drawRectangle(new TerminalPosition(CHART_POSITION_X - 1, CHART_POSITION_Y - 1),
                new TerminalSize((chartsData.get(activeChart).length + CHART_WIDTH) * 4, 50 + 4), '█');
    }

    private static void drawWeatherChartsLabel() throws IOException {
        ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());
        TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();

        drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - WEATHER_CHARTS_HEADER[0].length()) / 2, DEFAULT_HEADER_POSITION_Y), WEATHER_CHARTS_HEADER);
    }

    private static void checkIfViewShouldBeActive() {
        if (actualKey != null && (actualKey.getKeyType().equals(KeyType.Escape))) {
            isChartViewActive = false;
        }
    }
    private static void drawWeatherChartsView() throws IOException, InterruptedException {
        ViewManager.getTerminal().clearScreen();
        ViewManager.getTerminal().flush();

        while (isChartViewActive) {
            ViewManager.getTerminal().clearScreen();
            actualKey = ViewManager.getTerminal().pollInput();
            manageActiveButtonPosition();
            drawChartOptions();
            drawWeatherChartsLabel();
            drawWeatherChartsOverlay();
            drawTerminalOverlay();
            drawTemperaturesChart();
            ViewManager.getTerminal().flush();
            checkIfViewShouldBeActive();
            Thread.sleep(100);
        }
    }

    @Override
    public void display() throws IOException, InterruptedException {
        ViewManager.getTerminal().clearScreen();
        drawWeatherChartsView();
        ViewManager.getTerminal().flush();
    }
}
