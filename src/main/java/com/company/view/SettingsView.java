package com.company.view;


import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class SettingsView implements View{
    private static void drawSettingsView() throws IOException {
        ViewManager.getTerminal().clearScreen();
        ViewManager.getTerminal().flush();

        KeyStroke keyStroke = ViewManager.getTerminal().readInput();

        while (keyStroke != null && keyStroke.getKeyType() != KeyType.Escape) {
            keyStroke =  ViewManager.getTerminal().readInput();
        }
    }
    @Override
    public void display() throws IOException {
        ViewManager.getTerminal().clearScreen();
        drawSettingsView();
        ViewManager.getTerminal().flush();
    }
}
