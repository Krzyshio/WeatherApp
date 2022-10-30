package com.company.view;


import com.company.colour.picker.ColourPicker;
import com.company.commons.Component;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.company.commons.DrawingHelper.drawAsciiArt;
import static com.company.commons.DrawingHelper.drawTerminalOverlay;
import static com.company.view.ViewManager.getAppMainColour;
import static com.company.view.ViewManager.getAppSecondColour;

public class SettingsView implements View {
    private static final Integer DEFAULT_HEADER_POSITION_Y = 3;
    public static final Integer DEFAULT_OVERLAY_PADDING = 20;
    public static final Integer DEFAULT_SETTINGS_PADDING = 2;
    private static boolean isSettingsViewActive = true;
    private static Integer activeSetting = 0;
    private static KeyStroke actualKey;
    private static final String[] SETTINGS_HEADER = {
            " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄ ",
            "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌",
            "▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀  ▀▀▀▀█░█▀▀▀▀  ▀▀▀▀█░█▀▀▀▀  ▀▀▀▀█░█▀▀▀▀ ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀ ",
            "▐░▌          ▐░▌               ▐░▌          ▐░▌          ▐░▌     ▐░▌▐░▌    ▐░▌▐░▌          ▐░▌          ",
            "▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄      ▐░▌          ▐░▌          ▐░▌     ▐░▌ ▐░▌   ▐░▌▐░▌ ▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄ ",
            "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌          ▐░▌          ▐░▌     ▐░▌  ▐░▌  ▐░▌▐░▌▐░░░░░░░░▌▐░░░░░░░░░░░▌",
            " ▀▀▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀      ▐░▌          ▐░▌          ▐░▌     ▐░▌   ▐░▌ ▐░▌▐░▌ ▀▀▀▀▀▀█░▌ ▀▀▀▀▀▀▀▀▀█░▌",
            "          ▐░▌▐░▌               ▐░▌          ▐░▌          ▐░▌     ▐░▌    ▐░▌▐░▌▐░▌       ▐░▌          ▐░▌",
            " ▄▄▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄      ▐░▌          ▐░▌      ▄▄▄▄█░█▄▄▄▄ ▐░▌     ▐░▐░▌▐░█▄▄▄▄▄▄▄█░▌ ▄▄▄▄▄▄▄▄▄█░▌",
            "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌          ▐░▌     ▐░░░░░░░░░░░▌▐░▌      ▐░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌",
            " ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀       ▀            ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀ ",
    };
    private static final String[] MAIN_COLOUR_SETTING_LABEL = {
            "  __  __       _                   _                  ",
            " |  \\/  |     (_)                 | |                 ",
            " | \\  / | __ _ _ _ __     ___ ___ | | ___  _   _ _ __ ",
            " | |\\/| |/ _` | | '_ \\   / __/ _ \\| |/ _ \\| | | | '__|",
            " | |  | | (_| | | | | | | (_| (_) | | (_) | |_| | |   ",
            " |_|  |_|\\__,_|_|_| |_|  \\___\\___/|_|\\___/ \\__,_|_|   ",
    };

    private static final String[] SECOND_COLOUR_SETTING_LABEL = {
            "   _____                          _             _                  ",
            "  / ____|                        | |           | |                 ",
            " | (___   ___  ___ ___  _ __   __| |   ___ ___ | | ___  _   _ _ __ ",
            "  \\___ \\ / _ \\/ __/ _ \\| '_ \\ / _` |  / __/ _ \\| |/ _ \\| | | | '__|",
            "  ____) |  __/ (_| (_) | | | | (_| | | (_| (_) | | (_) | |_| | |   ",
            " |_____/ \\___|\\___\\___/|_| |_|\\__,_|  \\___\\___/|_|\\___/ \\__,_|_|   ",
    };


    private static Map<Integer, Component> settingComponents = new HashMap<>();

    static {
        settingComponents.put(0, new ColourPicker(MAIN_COLOUR_SETTING_LABEL,
                new TerminalPosition(DEFAULT_OVERLAY_PADDING + DEFAULT_SETTINGS_PADDING, DEFAULT_OVERLAY_PADDING + DEFAULT_SETTINGS_PADDING)));

        settingComponents.put(1, new ColourPicker(SECOND_COLOUR_SETTING_LABEL,
                new TerminalPosition(DEFAULT_OVERLAY_PADDING + DEFAULT_SETTINGS_PADDING, DEFAULT_OVERLAY_PADDING + DEFAULT_SETTINGS_PADDING + 10)));
    }

    private static void drawSettingsHeader() throws IOException {
        ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());
        TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();

        drawAsciiArt(new TerminalPosition((terminalSize.getColumns() - SETTINGS_HEADER[0].length()) / 2, DEFAULT_HEADER_POSITION_Y), SETTINGS_HEADER);
    }

    public static void drawSettingsOverlay() throws IOException {
        ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());

        TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();
        ViewManager.getTextGraphics().drawRectangle(new TerminalPosition(DEFAULT_OVERLAY_PADDING, DEFAULT_OVERLAY_PADDING),
                new TerminalSize(terminalSize.getColumns() - 2 * DEFAULT_OVERLAY_PADDING, terminalSize.getRows() - 2 * DEFAULT_OVERLAY_PADDING), '█');
    }

    private static void drawSettingComponents() throws IOException {
        settingComponents.values().forEach(Component::display);
        settingComponents.get(activeSetting).mark();
    }

    private static void manageActiveButtonPosition() {
        if (actualKey == null)
            return;
        if (actualKey.getKeyType() == KeyType.ArrowDown) {
            activeSetting = activeSetting < settingComponents.size() - 1 ? activeSetting + 1 : 0;
        }
        if (actualKey.getKeyType() == KeyType.ArrowUp) {
            activeSetting = activeSetting > 0 ? activeSetting - 1 : settingComponents.size() - 1;
        }
    }

    private static void manageColourChange() {
        if (actualKey == null)
            return;
        if (settingComponents.get(activeSetting) instanceof ColourPicker colourPicker) {
            if (actualKey.getKeyType() == KeyType.ArrowLeft) {
                colourPicker.setPrevColour();
            }
            if (actualKey.getKeyType() == KeyType.ArrowRight) {
                colourPicker.setNextColour();
            }
        }
    }

    private static void checkIfViewShouldBeActive() {
        if (actualKey != null && (actualKey.getKeyType().equals(KeyType.Escape))) {
            isSettingsViewActive = false;
        }
    }

    private static void setDefaultPickersColours() {
        if (settingComponents.get(0) instanceof ColourPicker colourPicker)
            colourPicker.setSelectedColour(getAppMainColour());
        if (settingComponents.get(1) instanceof ColourPicker colourPicker)
            colourPicker.setSelectedColour(getAppSecondColour());
    }

    private static void drawSettingsView() throws IOException, InterruptedException {
        setDefaultPickersColours();
        ViewManager.getTerminal().clearScreen();
        ViewManager.getTerminal().flush();

        while (isSettingsViewActive) {
            ViewManager.getTerminal().clearScreen();
            actualKey = ViewManager.getTerminal().pollInput();
            drawSettingsHeader();
            drawTerminalOverlay();
            drawSettingsOverlay();
            drawSettingComponents();
            manageActiveButtonPosition();
            manageColourChange();
            checkIfViewShouldBeActive();
            ViewManager.getTerminal().flush();
            //todo refactor busy-waiting
            Thread.sleep(100);
        }
        isSettingsViewActive = true;
    }

    @Override
    public void display() throws IOException, InterruptedException {
        ViewManager.getTerminal().clearScreen();
        drawSettingsView();
        ViewManager.getTerminal().flush();
    }
}
