package com.company.button;

import com.company.view.View;

import java.io.IOException;

public class NavigateToViewButton extends Button {
    private final View view;

    public NavigateToViewButton(String[] label, View view) {
        super(label);
        this.view = view;
    }

    @Override
    public void click() throws InterruptedException, IOException {
        view.display();
    }
}
