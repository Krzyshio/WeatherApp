package com.company.button;

import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

public interface Clickable {

    void click() throws IOException, InterruptedException;
    void display(TerminalPosition terminalPosition);
    Integer getLabelLength();
}
