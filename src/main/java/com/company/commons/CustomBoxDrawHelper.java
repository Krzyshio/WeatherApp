package com.company.commons;

import com.company.view.ViewManager;
import com.googlecode.lanterna.TerminalPosition;

public class CustomBoxDrawHelper {

    public static void drawCustomBox(TerminalPosition terminalPosition, Integer height, Integer width) {
        drawVerticalBoxLines(terminalPosition, height, width);
        drawHorizontalBoxLines(terminalPosition, height, width);
        drawBoxEdges(terminalPosition, height, width);
    }

    private static void drawVerticalBoxLines(TerminalPosition terminalPosition, Integer height, Integer width) {
        ViewManager.getTextGraphics().drawLine(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()),
                new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow() + height), '│');

        ViewManager.getTextGraphics().drawLine(new TerminalPosition(terminalPosition.getColumn() + width, terminalPosition.getRow()),
                new TerminalPosition(terminalPosition.getColumn() + width, terminalPosition.getRow() + height), '│');
    }

    private static void drawHorizontalBoxLines(TerminalPosition terminalPosition, Integer height, Integer width) {
        ViewManager.getTextGraphics().drawLine(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()),
                new TerminalPosition(terminalPosition.getColumn() + width, terminalPosition.getRow()), '─');

        ViewManager.getTextGraphics().drawLine(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow() + height),
                new TerminalPosition(terminalPosition.getColumn() + width, terminalPosition.getRow() + height), '─');
    }

    private static void drawBoxEdges(TerminalPosition terminalPosition, Integer height, Integer width) {
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn(),terminalPosition.getRow(), '┌');
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn() + width,terminalPosition.getRow(), '┐');
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn(),terminalPosition.getRow() + height, '└');
        ViewManager.getTextGraphics().setCharacter(terminalPosition.getColumn() + width,terminalPosition.getRow() + height, '┘');
    }

}
