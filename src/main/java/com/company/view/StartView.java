package com.company.view;

import com.company.button.Button;
import com.company.icon.WeatherTypeIcon;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.commons.DrawingHelper.drawAsciiArt;
import static com.company.commons.DrawingHelper.drawTerminalOverlay;

public class StartView extends ViewModel {

    private static final Integer DEFAULT_HEADER_POSITION_Y = 3;

    private static final Map<Integer, Button> buttons = new HashMap<>();

    private static final String[] HEADER = {
            " ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄          ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄",
            "▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌        ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌",
            "▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌        ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌",
            "▐░▌       ▐░▌▐░▌          ▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌          ▐░▌       ▐░▌        ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌",
            "▐░▌   ▄   ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌        ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌",
            "▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌        ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌",
            "▐░▌ ▐░▌░▌ ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌     ▐░▌     ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀█░█▀▀         ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀",
            "▐░▌▐░▌ ▐░▌▐░▌▐░▌          ▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌          ▐░▌     ▐░▌          ▐░▌       ▐░▌▐░▌          ▐░▌",
            "▐░▌░▌   ▐░▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌      ▐░▌         ▐░▌       ▐░▌▐░▌          ▐░▌",
            "▐░░▌     ▐░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌        ▐░▌       ▐░▌▐░▌          ▐░▌",
            " ▀▀       ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀       ▀       ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀          ▀         ▀  ▀            ▀"
    };

    private static final String[] SELECT_OPTIONS_LABEL = {
            "███████ ███████ ██      ███████  ██████ ████████      ██████  ██████  ████████ ██  ██████  ███    ██ ███████",
            "██      ██      ██      ██      ██         ██        ██    ██ ██   ██    ██    ██ ██    ██ ████   ██ ██      ██",
            "███████ █████   ██      █████   ██         ██        ██    ██ ██████     ██    ██ ██    ██ ██ ██  ██ ███████   ",
            "     ██ ██      ██      ██      ██         ██        ██    ██ ██         ██    ██ ██    ██ ██  ██ ██      ██ ██",
            "███████ ███████ ███████ ███████  ██████    ██         ██████  ██         ██    ██  ██████  ██   ████ ███████"
    };

    private static final String[] WEATHER_DETAILS_LABEL = {
            "██     ██ ███████  █████  ████████ ██   ██ ███████ ██████      ███    ██ ██████  ███████ ████████  █████  ██ ██      ███████",
            "██     ██ ██      ██   ██    ██    ██   ██ ██      ██   ██     ████   ██ ██   ██ ██         ██    ██   ██ ██ ██      ██     ",
            "██  █  ██ █████   ███████    ██    ███████ █████   ██████      ██ ██  ██ ██   ██ █████      ██    ███████ ██ ██      ███████",
            "██ ███ ██ ██      ██   ██    ██    ██   ██ ██      ██   ██     ██  ██ ██ ██   ██ ██         ██    ██   ██ ██ ██           ██",
            " ███ ███  ███████ ██   ██    ██    ██   ██ ███████ ██   ██     ██   ████ ██████  ███████    ██    ██   ██ ██ ███████ ███████"
    };

    private static final String[] SETTINGS_LABEL = {
            "███████ ███████ ████████ ████████ ██ ███    ██  ██████  ███████",
            "██      ██         ██       ██    ██ ████   ██ ██       ██     ",
            "███████ █████      ██       ██    ██ ██ ██  ██ ██   ███ ███████",
            "     ██ ██         ██       ██    ██ ██  ██ ██ ██    ██      ██",
            "███████ ███████    ██       ██    ██ ██   ████  ██████  ███████"
    };

    static {
        buttons.put(0, new Button(WEATHER_DETAILS_LABEL, new WeatherOverview()));
        buttons.put(1, new Button(SETTINGS_LABEL, new WeatherOverview()));
        buttons.put(2, new Button(SETTINGS_LABEL, new WeatherOverview()));
        buttons.put(3, new Button(WEATHER_DETAILS_LABEL, new WeatherOverview()));
    }

    private static void drawMenuButtons(TerminalSize terminalSize) {
        AtomicInteger i = new AtomicInteger();

        buttons.values().forEach(button -> button.display(new TerminalPosition(
                (terminalSize.getColumns() - button.getLabelLength()) / 2,
                40 + 10 * i.getAndIncrement())));
    }

    private static void drawAnimatedIcons(TerminalPosition terminalPosition, Integer delta) throws IOException {
        AtomicInteger i = new AtomicInteger();
        int terminalColumns = ViewManager.getTerminal().getTerminalSize().getColumns();

        Arrays.stream(WeatherTypeIcon.values()).forEach(icon -> {
            int xPosition = (terminalPosition.getColumn() + delta + i.get() * (terminalColumns / WeatherTypeIcon.values().length)) % terminalColumns;

            icon.draw(new TerminalPosition(xPosition, terminalPosition.getRow()));
            if (xPosition + icon.getLength() > terminalColumns) {
                icon.draw(new TerminalPosition(xPosition - terminalColumns, terminalPosition.getRow()));

            }
            i.getAndIncrement();
        });
    }

    @Override
    public void display() throws IOException, InterruptedException {
        KeyStroke keyStroke = ViewManager.getTerminal().readInput();
        AtomicInteger delta = new AtomicInteger();

        while (keyStroke.getKeyType() != KeyType.Enter) {
            TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();

            ViewManager.getTerminal().clearScreen();
            ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            drawMenuButtons(terminalSize);
            drawAnimatedIcons(new TerminalPosition(0, 20), delta.getAndIncrement());
            drawTerminalOverlay();
            ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - SELECT_OPTIONS_LABEL[0].length()) / 2, 30), SELECT_OPTIONS_LABEL);
            drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - HEADER[0].length()) / 2, DEFAULT_HEADER_POSITION_Y), HEADER);
            ViewManager.getTerminal().flush();
            //todo refactor busy-waiting
            Thread.sleep(100);
        }
        buttons.get(0).click();
    }
}

