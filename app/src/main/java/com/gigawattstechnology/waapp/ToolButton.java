package com.gigawattstechnology.waapp;

import android.widget.Button;

public class ToolButton {
    Button button;
    int idButton;

    public ToolButton(Button button, int idButton) {
        this.button = button;
        this.idButton = idButton;
    }

    public Button getButton() {
        return button;
    }

    public int getIdButton() {
        return idButton;
    }
}
