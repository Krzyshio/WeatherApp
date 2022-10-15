package com.company.commons;

import com.company.view.ViewManager;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Icon {
    public static String[] ICON = {
            "   \\   /  ",
            "    .-.    ",
            " ― (   ) ― ",
            "    `-’    ",
            "    /  \\   "
    };

    public static void drawIcon(TerminalPosition terminalPosition){
        AtomicInteger i = new AtomicInteger();

        Arrays.stream(ICON).forEach(line -> {
            i.getAndIncrement();
            ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get(), line, SGR.BOLD);
        });
    }
}
