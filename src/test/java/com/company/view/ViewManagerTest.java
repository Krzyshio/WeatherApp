package com.company.view;

import com.googlecode.lanterna.TerminalSize;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ViewManagerTest {

    @Test
    @org.junit.jupiter.api.Test
    public void shouldSetDefaultValuesWhenCreated() throws IOException {
        ViewManager testViewManager = new ViewManager();

        assertNotNull(testViewManager);
        assertNotNull(testViewManager.getTerminal());
        assertEquals(testViewManager.getTerminal().getTerminalSize(), new TerminalSize(200, 64));
    }

}