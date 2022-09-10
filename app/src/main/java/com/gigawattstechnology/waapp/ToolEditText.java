package com.gigawattstechnology.waapp;

import android.widget.EditText;

public class ToolEditText {
    EditText editText;
    int idEditText;

    public ToolEditText(EditText editText, int idEditText) {
        this.editText = editText;
        this.idEditText = idEditText;
    }

    public EditText getEditText() {
        return editText;
    }

    public int getIdEditText() {
        return idEditText;
    }
}
