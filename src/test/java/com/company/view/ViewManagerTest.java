package com.company.view;

import com.googlecode.lanterna.TerminalSize;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ViewManagerTest {

    @Test
    void shouldSetDefaultValuesWhenCreated() throws IOException {
        ViewManager testViewManager = new ViewManager();

        assertNotNull(testViewManager);
        assertNotNull(ViewManager.getTerminal());
        assertEquals(new TerminalSize(201, 101), ViewManager.getTerminal().getTerminalSize());
    }

}