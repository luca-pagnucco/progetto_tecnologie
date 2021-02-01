package com.example.progetto5cia_pagnucco;

import android.graphics.Path;

public class FingerPath {
    public int color;
    public boolean fill;
    public int strokeWidth;
    public Path path;

    public FingerPath(int color, boolean fill, int strokeWidth, Path path) {
        this.color = color;
        this.fill = fill;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
