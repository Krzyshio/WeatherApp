package com.company.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    //todo enum refactor
    private static Integer actualView = 1;

    private static final Map<Integer, ViewModel> views = new HashMap<>();

    static {
        views.put(0, new StartView());
        views.put(1, new WeatherOverview());
    }

    private static final String APP_TITLE = "Weather App";
    private static final Integer DEFAULT_TERMINAL_LENGTH = 201;
    private static final Integer DEFAULT_TERMINAL_WIDTH = 101;

    private static final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory()
            .setTerminalEmulatorTitle(APP_TITLE)
            .setInitialTerminalSize(new TerminalSize(DEFAULT_TERMINAL_LENGTH, DEFAULT_TERMINAL_WIDTH));

    private static Terminal terminal = null;
    private static TextGraphics textGraphics = null;

    public ViewManager() {
        try {
            terminal = defaultTerminalFactory.createTerminal();
            textGraphics = terminal.newTextGraphics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initTerminal() throws IOException {
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.setBackgroundColor(new TextColor.RGB(0, 83, 159));
        terminal.enterPrivateMode();
        terminal.clearScreen();
        terminal.setCursorVisible(false);
    }

    public void display() {
        try {
            initTerminal();
            views.get(0).display();
            KeyStroke keyStroke = terminal.readInput();

            while (keyStroke.getKeyType() != KeyType.Escape) {

                keyStroke = terminal.readInput();
                views.get(actualView).display();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Terminal getTerminal() {
        return terminal;
    }

    public static TextGraphics getTextGraphics() {
        return textGraphics;
    }
}
