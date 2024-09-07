package com.godpalace.fastlibrary.gui.swing.drawpanel;

import java.awt.*;

public class Pixel {
    public int x, y, w, h;
    public Color color;

    public Pixel(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pixel) {
            Pixel p = (Pixel) obj;
            return p.x == x && p.y == y && p.color.equals(color) && p.w == w && p.h == h;
        } else {
            return false;
        }
    }
}
