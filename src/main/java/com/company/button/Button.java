package com.company.button;

import com.company.commons.CustomBoxDrawHelper;
import com.company.view.View;
import com.company.view.ViewManager;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import static com.company.commons.DrawingHelper.drawAsciiArt;

public class Button {
    private final String[] label;
    private final View view;
    private TerminalPosition terminalPosition;

    public Button(String[] label, View view) {
        this.label = label;
        this.view = view;
    }

    public void display() {
        CustomBoxDrawHelper.drawCustomBox(terminalPosition, label.length + 1, getLabelLength() + 3);
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn() + 2, terminalPosition.getRow()), label);
    }

    public void fill() {
        ViewManager.getTextGraphics().fillRectangle(terminalPosition,
                new TerminalSize(getLabelLength() + 4, label.length + 2), '█');
    }

    public void click() throws IOException, InterruptedException {
        view.display();
    }

    public Integer getLabelLength() {
        return Arrays.stream(label).max(Comparator.comparingInt(String::length)).get().length();
    }

    public void setTerminalPosition(TerminalPosition terminalPosition) {
        this.terminalPosition = terminalPosition;
    }
}
