package com.company.commons;

import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

public abstract class Component {
    protected String[] label;
    protected TerminalPosition terminalPosition;

    public void display() {
    }

    public void mark() throws IOException {
    }
}
