package com.gigawattstechnology.waapp;

import android.widget.ImageView;

public class ToolImageView {
    ImageView imageView;
    int idImageView;

    public ToolImageView(ImageView imageView, int idImageView) {
        this.imageView = imageView;
        this.idImageView = idImageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getIdImageView() {
        return idImageView;
    }
}
