package com.company.colour.picker;

import com.company.commons.Component;
import com.company.commons.CustomBoxDrawHelper;
import com.company.view.ViewManager;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;

import static com.company.commons.DrawingHelper.drawAsciiArt;
import static com.company.view.SettingsView.DEFAULT_OVERLAY_PADDING;
import static com.company.view.SettingsView.DEFAULT_SETTINGS_PADDING;
import static com.company.view.ViewManager.getAppMainColour;
import static com.company.view.ViewManager.getAppSecondColour;

public class ColourPicker extends Component {
    protected static final TextColor.ANSI[] values = TextColor.ANSI.values();
    private static final String[] COLOUR_PREVIEW = {
            "████████",
            "████████",
            "████████",
            "████████"};

    private static final String[] LEFT_ARROW = {
            "  __  ",
            " / /  ",
            "/ /   ",
            "\\ \\ ",
            " \\_\\",
    };

    private static final String[] RIGHT_ARROW = {
            "__",
            "\\ \\",
            " \\ \\",
            " / /",
            "/_/",
    };
    private TextColor.ANSI selectedColour;

    public ColourPicker(String[] label, TerminalPosition terminalPosition) {
        this.label = label;
        this.terminalPosition = terminalPosition;
    }

    public void drawArrows() {
        ViewManager.getTextGraphics().setForegroundColor(getAppSecondColour());
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn() + 134, terminalPosition.getRow()), LEFT_ARROW);
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn() + 150, terminalPosition.getRow()), RIGHT_ARROW);
    }

    public void drawColourPreview() {
        ViewManager.getTextGraphics().setForegroundColor(selectedColour);
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn() + 140, terminalPosition.getRow() + 1), COLOUR_PREVIEW);
    }

    @Override
    public void display() {
        ViewManager.getTextGraphics().setForegroundColor(getAppMainColour());
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()), label);
        drawColourPreview();
        drawArrows();
    }

    @Override
    public void mark() throws IOException {
        ViewManager.getTextGraphics().setForegroundColor(getAppSecondColour());

        TerminalSize terminalSize = ViewManager.getTerminal().getTerminalSize();

        CustomBoxDrawHelper.drawCustomBox(terminalPosition, label.length + 1,
                terminalSize.getColumns() - 2 * DEFAULT_OVERLAY_PADDING - 2 * DEFAULT_SETTINGS_PADDING - 1);
    }

    public void setPrevColour() {
        selectedColour = values[(selectedColour.ordinal() - 1 + values.length) % values.length];
    }

    public void setNextColour() {
        selectedColour = values[(selectedColour.ordinal() + 1 + values.length) % values.length];
    }

    public void setSelectedColour(TextColor.ANSI selectedColour) {
        this.selectedColour = selectedColour;
    }

    public TextColor.ANSI getSelectedColour() {
        return selectedColour;
    }
}
