package com.company.colour.picker;

import com.company.commons.Component;
import com.company.commons.CustomBoxDrawHelper;
import com.googlecode.lanterna.TerminalPosition;

import static com.company.commons.DrawingHelper.drawAsciiArt;

public class ColourPicker extends Component {

    public ColourPicker(TerminalPosition terminalPosition) {
        this.terminalPosition = terminalPosition;
    }

    @Override
    public void display() {
        drawAsciiArt(new TerminalPosition(terminalPosition.getColumn() + 2, terminalPosition.getRow()), label);
    }

    @Override
    public void mark() {
        CustomBoxDrawHelper.drawCustomBox(terminalPosition, label.length + 1, 11);
    }
}
