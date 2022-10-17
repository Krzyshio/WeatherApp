package com.company.button;

import com.company.commons.CustomBoxDrawHelper;
import com.company.view.View;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import static com.company.commons.DrawingHelper.drawAsciiArt;

public class Button implements Clickable {
    private final String[] label;
    private final View view;

    public Button(String[] label, View view) {
        this.label = label;
        this.view = view;
    }

    @Override
    public void display(TerminalPosition terminalPosition) {
        CustomBoxDrawHelper.drawCustomBox(terminalPosition, label.length + 1, getLabelLength() + 3);
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn() + 2, terminalPosition.getRow()), label);
    }

    @Override
    public void click() throws IOException, InterruptedException {
        view.display();
    }

    @Override
    public Integer getLabelLength() {
        return Arrays.stream(label).max(Comparator.comparingInt(String::length)).get().length();
    }
}
