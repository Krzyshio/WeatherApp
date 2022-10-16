package com.company.commons;

import com.company.view.ViewManager;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class WeatherDrawingHelper {

    public static void drawSomeBolts(TerminalPosition terminalPosition) {
        ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn() + 5, terminalPosition.getRow() + 4, '⚡');
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn() + 8, terminalPosition.getRow() + 4, '⚡');
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn() + 7, terminalPosition.getRow() + 5, '⚡');
    }

    public static void drawCloud(TerminalPosition terminalPosition) {
        final String[] cloud = {
                "  .-.   ",
                " (   ). ",
                "(___(__)"
        };
        AtomicInteger i = new AtomicInteger();

        ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        Arrays.stream(cloud)
                .limit(3)
                .forEach(line -> {
                    i.getAndIncrement();
                    ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get(), line, SGR.BOLD);
                });
    }

    public static void drawRain(TerminalPosition terminalPosition) {
        final String[] rain = {
                "‚ʻ‚ʻ‚ʻ‚ʻ",
                "‚ʻ‚ʻ‚ʻ‚ʻ",
        };
        AtomicInteger i = new AtomicInteger();

        ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.BLUE);

        Arrays.stream(rain)
                .forEach(line -> {
                    i.getAndIncrement();
                    ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get(), line, SGR.BOLD);
                });
    }

    public static void drawSnow(TerminalPosition terminalPosition) {
        final String[] rain = {
                " * * * *",
                "* * * * ",
        };
        AtomicInteger i = new AtomicInteger();

        ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);

        Arrays.stream(rain)
                .forEach(line -> {
                    i.getAndIncrement();
                    ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get(), line, SGR.BOLD);
                });
    }

    private WeatherDrawingHelper() {
    }
}
