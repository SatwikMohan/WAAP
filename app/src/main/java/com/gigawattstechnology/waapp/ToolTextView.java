package com.gigawattstechnology.waapp;

import android.widget.TextView;

import org.w3c.dom.Text;

public class ToolTextView {
    TextView textView;
    int idTextView;

    public ToolTextView(TextView textView, int idTextView) {
        this.textView = textView;
        this.idTextView = idTextView;
    }

    public TextView getTextView() {
        return textView;
    }

    public int getIdTextView() {
        return idTextView;
    }
}
