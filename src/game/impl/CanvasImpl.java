/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.impl;

import game.Game;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author nocrail
 */
public class CanvasImpl implements Game.Canvas {

    public static final int SOLID = 0;
    public static final int DOTTED = 1;
    public static final int HCENTER = 1;
    public static final int TOP = 16;
    public static final int VCENTER = 2;
    public static final int BOTTOM = 32;
    public static final int LEFT = 4;
    public static final int BASELINE = 64;
    public static final int RIGHT = 8;
    

    private Graphics g;
    private Canvas c;

    public CanvasImpl(Graphics g, Canvas c) {
        this.g = g;
        this.c = c;
    }

    public void setColor(int r, int g, int b, int a) {
        if (r > 255)
            r = 255;
        if (g > 255)
            g = 255;
        if (b > 255)
            b = 255;
        this.g.setColor(r, g, b);
    }

    public void setColor(int color) {
        this.g.setColor(color);
    }

    public void drawRect(int x, int y, int w, int h) {
        this.g.fillRect(x, y, w, h);
    }

    public void drawImage(Object imageData, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest) {
        this.g.drawRegion((Image) imageData, x_src, y_src, width, height, transform, x_dest, y_dest, TOP | LEFT);
    }

    public void drawText(int x, int y, String text) {
        this.g.drawString(text, x, y, TOP | LEFT);
    }

    public int getWidth() {
        return c.getWidth();
    }

    public int getHeight() {
        return c.getHeight();
    }

}
