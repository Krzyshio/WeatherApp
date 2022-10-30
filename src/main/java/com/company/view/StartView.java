package com.company.view;

import com.company.button.Button;
import com.company.icon.WeatherTypeIcon;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.commons.DrawingHelper.drawAsciiArt;
import static com.company.commons.DrawingHelper.drawTerminalOverlay;
import static com.company.view.ViewManager.getAppMainColour;

public class StartView implements View {

    private static final Integer DEFAULT_HEADER_POSITION_Y = 3;
    private static final Integer DEFAULT_BUTTONS_PADDING = 10;
    private static final Integer DEFAULT_BUTTONS_START_POSITION = 55;
    private static final Integer DEFAULT_SELECT_OPTIONS_LABEL_POSITION_Y = 40;
    private static Integer activeButton = 0;
    private static final Map<Integer, Button> buttons = new HashMap<>();
    private static boolean isStartViewActive = true;
    private static KeyStroke actualKey;
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

    private static final String[] WEATHER_CHART_LABEL = {
            "██     ██ ███████  █████  ████████ ██   ██ ███████ ██████       ██████ ██   ██  █████  ██████  ████████",
            "██     ██ ██      ██   ██    ██    ██   ██ ██      ██   ██     ██      ██   ██ ██   ██ ██   ██    ██   ",
            "██  █  ██ █████   ███████    ██    ███████ █████   ██████      ██      ███████ ███████ ██████     ██   ",
            "██ ███ ██ ██      ██   ██    ██    ██   ██ ██      ██   ██     ██      ██   ██ ██   ██ ██   ██    ██   ",
            " ███ ███  ███████ ██   ██    ██    ██   ██ ███████ ██   ██      ██████ ██   ██ ██   ██ ██   ██    ██   "
    };

    private static final String[] SETTINGS_LABEL = {
            "███████ ███████ ████████ ████████ ██ ███    ██  ██████  ███████",
            "██      ██         ██       ██    ██ ████   ██ ██       ██     ",
            "███████ █████      ██       ██    ██ ██ ██  ██ ██   ███ ███████",
            "     ██ ██         ██       ██    ██ ██  ██ ██ ██    ██      ██",
            "███████ ███████    ██       ██    ██ ██   ████  ██████  ███████"
    };

    private static final String[] EXIT_LABEL = {
            "███████ ██   ██ ██ ████████",
            "██       ██ ██  ██    ██   ",
            "█████     ███   ██    ██   ",
            "██       ██ ██  ██    ██   ",
            "███████ ██   ██ ██    ██   "
    };

    static {
        buttons.put(0, new Button(WEATHER_DETAILS_LABEL, new WeatherOverview()));
        buttons.put(1, new Button(WEATHER_CHART_LABEL, new WeatherOverview()));
        buttons.put(2, new Button(SETTINGS_LABEL, new SettingsView()));
        buttons.put(3, new Button(EXIT_LABEL, new WeatherOverview()));
    }

    private static void drawMenuButtons(TerminalSize terminalSize) {
        refreshButtonsPositions(terminalSize);
        buttons.values().forEach(Button::display);
        buttons.get(activeButton).mark();
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

    private static void manageActiveButtonPosition() {
        if (actualKey == null)
            return;
        if (actualKey.getKeyType() == KeyType.ArrowDown) {
            activeButton = activeButton < buttons.size() - 1 ? activeButton + 1 : 0;
        }
        if (actualKey.getKeyType() == KeyType.ArrowUp) {
            activeButton = activeButton > 0 ? activeButton - 1 : buttons.size() - 1;
        }
    }

    private static void manageMenuClickEvent() throws IOException, InterruptedException {
        if (actualKey == null)
            return;
        if (actualKey.getKeyType() == KeyType.Enter) {
            buttons.get(activeButton).click();
        }
    }

    private static void checkIfViewShouldBeActive() {
        if (actualKey != null && (actualKey.getKeyType().equals(KeyType.Escape) || isExitButtonSelected())) {
            isStartViewActive = false;
        }
    }

    private static boolean isExitButtonSelected() {
        return actualKey != null && activeButton.equals(3) && actualKey.getKeyType().equals(KeyType.Enter);
    }

    @Override
    public void display() throws IOException, InterruptedException {
        AtomicInteger delta = new AtomicInteger();

        while (isStartViewActive) {
            TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();
            ViewManager.getTerminal().clearScreen();
            drawAnimatedIcons(new TerminalPosition(0, 25), delta.getAndIncrement());
            ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());
            drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - SELECT_OPTIONS_LABEL[0].length()) / 2, DEFAULT_SELECT_OPTIONS_LABEL_POSITION_Y), SELECT_OPTIONS_LABEL);
            drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - HEADER[0].length()) / 2, DEFAULT_HEADER_POSITION_Y), HEADER);
            ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());
            drawTerminalOverlay();
            drawMenuButtons(terminalSize);
            manageActiveButtonPosition();
            manageMenuClickEvent();
            actualKey = ViewManager.getTerminal().pollInput();
            checkIfViewShouldBeActive();
            ViewManager.getTerminal().flush();
            //todo refactor busy-waiting
            Thread.sleep(100);
        }
        isStartViewActive = true;
    }
}
