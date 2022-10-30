package com.company.icon;

import com.company.view.ViewManager;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.commons.DrawingHelper.*;

public enum WeatherTypeIcon implements Icon {

    CLEAR_SKY {
        protected static final String[] SUNNY = {
                "   \\   /  ",
                "    .-.    ",
                " ― (   ) ― ",
                "    `-’    ",
                "    /  \\   "
        };

        @Override
        public void draw(TerminalPosition terminalPosition) {
            AtomicInteger i = new AtomicInteger();

            Arrays.stream(SUNNY).forEach(line -> {
                i.getAndIncrement();
                ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.YELLOW);
                ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get(), line, SGR.BOLD);
            });
        }

        @Override
        public Integer getLength() {
            return 11;
        }
    },
    FEW_CLOUDS {
        protected static final String[] FEW_CLOUDS = {
                "   \\  /",
                " _ /\"\"",
                "   \\_",
                "   /",
        };

        @Override
        public void draw(TerminalPosition terminalPosition) {
            AtomicInteger i = new AtomicInteger();
            drawCloud(new TerminalPosition(terminalPosition.getColumn() + 4, terminalPosition.getRow()));

            Arrays.stream(FEW_CLOUDS).forEach(line -> {
                i.getAndIncrement();
                ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.YELLOW);
                ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get() - 1, line, SGR.BOLD);
            });
        }

        @Override
        public Integer getLength() {
            return 7;
        }
    },
    SCATTERED_CLOUDS {
        @Override
        public void draw(TerminalPosition terminalPosition) {
            drawCloud(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()));
        }

        @Override
        public Integer getLength() {
            return DEFAULT_ICON_SIZE;
        }
    },
    SHOWER_RAIN {
        @Override
        public void draw(TerminalPosition terminalPosition) {
            drawCloud(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow() - 1));
            drawCloud(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()));
        }

        @Override
        public Integer getLength() {
            return DEFAULT_ICON_SIZE;
        }
    },
    BROKEN_CLOUDS {
        @Override
        public void draw(TerminalPosition terminalPosition) {
            drawCloud(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow() - 1));
            drawCloud(new TerminalPosition(terminalPosition.getColumn(), terminalPosition.getRow()));
            drawRain(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow() + 3));
        }

        @Override
        public Integer getLength() {
            return DEFAULT_ICON_SIZE;
        }
    },
    RAIN {
        @Override
        public void draw(TerminalPosition terminalPosition) {
            drawCloud(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow()));
            drawRain(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow() + 3));
        }

        @Override
        public Integer getLength() {
            return DEFAULT_ICON_SIZE;
        }
    },
    THUNDERSTORM {
        @Override
        public void draw(TerminalPosition terminalPosition) {

            drawCloud(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow()));
            drawRain(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow() + 3));
            drawSomeBolts(terminalPosition);
        }

        @Override
        public Integer getLength() {
            return DEFAULT_ICON_SIZE;
        }
    },
    SNOW {
        @Override
        public void draw(TerminalPosition terminalPosition) {

            drawCloud(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow()));
            drawSnow(new TerminalPosition(terminalPosition.getColumn() + 3, terminalPosition.getRow() + 3));
        }

        @Override
        public Integer getLength() {
            return DEFAULT_ICON_SIZE;
        }
    },
    MIST {
        protected static final String[] MISTY = {
                "    - _     ",
                "  _ - _ - _ ",
                " _ - _ - _ -",
                " _ - _ - _ -",
                "  _ - _ - _ ",
        };


        @Override
        public void draw(TerminalPosition terminalPosition) {
            AtomicInteger i = new AtomicInteger();

            Arrays.stream(MISTY)
                    .forEach(line -> {
                        i.getAndIncrement();
                        ViewManager.getTextGraphics().setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                        ViewManager.getTextGraphics().putString(terminalPosition.getColumn(), terminalPosition.getRow() + i.get(), line, SGR.BOLD);
                    });
        }

        @Override
        public Integer getLength() {
            return 12;
        }
    }
}
