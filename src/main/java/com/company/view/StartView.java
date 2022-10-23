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
    private static final Integer DEFAULT_BUTTONS_PADDING = 10;
    private static final Integer DEFAULT_BUTTONS_START_POSITION = 40;
    private static Integer activeButton = 0;

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
            "██     ██ ███████  █████  ████████ ██   ██ ███████ ██████      ██████  ███████ ████████  █████  ██ ██      ███████",
            "██     ██ ██      ██   ██    ██    ██   ██ ██      ██   ██     ██   ██ ██         ██    ██   ██ ██ ██      ██     ",
            "██  █  ██ █████   ███████    ██    ███████ █████   ██████      ██   ██ █████      ██    ███████ ██ ██      ███████",
            "██ ███ ██ ██      ██   ██    ██    ██   ██ ██      ██   ██     ██   ██ ██         ██    ██   ██ ██ ██           ██",
            " ███ ███  ███████ ██   ██    ██    ██   ██ ███████ ██   ██     ██████  ███████    ██    ██   ██ ██ ███████ ███████"
    };

    private static final String[] SETTINGS_LABEL = {
            "███████ ███████ ████████ ████████ ██ ███    ██  ██████  ███████",
            "██      ██         ██       ██    ██ ████   ██ ██       ██     ",
            "███████ █████      ██       ██    ██ ██ ██  ██ ██   ███ ███████",
            "     ██ ██         ██       ██    ██ ██  ██ ██ ██    ██      ██",
            "███████ ███████    ██       ██    ██ ██   ████  ██████  ███████"
    };

    private static void drawMenuButtons(TerminalSize terminalSize) {

        buttons.put(0, new Button(WEATHER_DETAILS_LABEL, new WeatherOverview()));
        buttons.put(1, new Button(SETTINGS_LABEL, new WeatherOverview()));
        buttons.put(2, new Button(WEATHER_DETAILS_LABEL, new WeatherOverview()));
        buttons.put(3, new Button(SETTINGS_LABEL, new WeatherOverview()));

        refreshButtonsPositions(terminalSize);
        buttons.values().forEach(Button::display);
        buttons.get(activeButton).fill();
    }

    public static void refreshButtonsPositions(TerminalSize terminalSize) {
        AtomicInteger i = new AtomicInteger();

        buttons.forEach((key, value) -> {
            value.setTerminalPosition(new TerminalPosition((terminalSize.getColumns() - value.getLabelLength()) / 2,
                    DEFAULT_BUTTONS_START_POSITION + DEFAULT_BUTTONS_PADDING * i.getAndIncrement()));
            buttons.put(key, value);
        });
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

    private static void manageActiveButtonPosition(KeyStroke keyStroke) {
        if (keyStroke == null)
            return;
        if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            activeButton = activeButton < buttons.size() - 1 ? activeButton + 1 : 0;
        }
        if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            activeButton = activeButton > 0 ? activeButton - 1 : buttons.size() - 1;
        }
    }

    private static void manageMenuClickEvent(KeyStroke keyStroke) throws IOException, InterruptedException {
        if (keyStroke == null)
            return;
        if (keyStroke.getKeyType() == KeyType.Enter) {
            buttons.get(activeButton).click();
        }
    }

    @Override
    public void display() throws IOException, InterruptedException {
        KeyStroke keyStroke = ViewManager.getTerminal().readInput();
        AtomicInteger delta = new AtomicInteger();

        while (keyStroke.getKeyType() != KeyType.Enter) {
            KeyStroke actualKey = ViewManager.getTerminal().pollInput();
            TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();
            ViewManager.getTerminal().clearScreen();
            drawAnimatedIcons(new TerminalPosition(0, 20), delta.getAndIncrement());
            ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - SELECT_OPTIONS_LABEL[0].length()) / 2, 30), SELECT_OPTIONS_LABEL);
            drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - HEADER[0].length()) / 2, DEFAULT_HEADER_POSITION_Y), HEADER);
            ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            drawTerminalOverlay();
            drawMenuButtons(terminalSize);
            manageActiveButtonPosition(actualKey);
            manageMenuClickEvent(actualKey);
            ViewManager.getTerminal().flush();
            //todo refactor busy-waiting
            Thread.sleep(100);
        }
    }
}

