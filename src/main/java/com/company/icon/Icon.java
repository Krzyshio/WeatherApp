package com.company.icon;

import com.googlecode.lanterna.TerminalPosition;

public interface Icon {
    Integer DEFAULT_ICON_SIZE = 8;

    void draw(TerminalPosition terminalPosition);

    Integer getLength();
}
