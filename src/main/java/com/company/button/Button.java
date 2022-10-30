package com.company.button;

import com.company.commons.Component;
import com.company.commons.CustomBoxDrawHelper;
import com.company.view.ViewManager;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import static com.company.commons.DrawingHelper.drawAsciiArt;
import static com.company.view.ViewManager.getAppMainColour;
import static com.company.view.ViewManager.getAppSecondColour;

public class Button extends Component {
    private Runnable action;

    public Button(String[] label) {
        this.label = label;
    }

    @Override
    public void display() {
        ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());

        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()), label);
    }

    @Override
    public void mark() {
        ViewManager.getTextGraphics().setForegroundColor(getAppSecondColour());

        CustomBoxDrawHelper.drawCustomBox(new TerminalPosition(terminalPosition.getColumn() - 2, terminalPosition.getRow()),
                label.length + 1, getLabelLength() + 3);
    }

    private static void executeButtonAction(Runnable runnable) {
        runnable.run();
    }

    public void click() throws InterruptedException, IOException {
        executeButtonAction(action);
    }


    public Integer getLabelLength() {
        return Arrays.stream(label).max(Comparator.comparingInt(String::length)).get().length();
    }

    public void setTerminalPosition(TerminalPosition terminalPosition) {
        this.terminalPosition = terminalPosition;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
}
