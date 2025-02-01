/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;


import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;


/**
 *
 * @author nocrail
 */
public class GameCanvas implements Game.Canvas {
    
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
    
    public GameCanvas(Graphics g, Canvas c){
        this.g = g;
        this.c = c;
    }

    public void setColor(int r, int g, int b, int a) {
        this.g.setColor(r, g, b);
    }

    public void drawRect(int x, int y, int w, int h) {
        this.g.fillRect(x, y, w, h);
    }

    public void drawText(int x, int y, String text) {
        this.g.drawString(text, x, y, TOP|LEFT);
    }

    public int getWidth() {
        return c.getWidth();
    }

    public int getHeight() {
        return c.getHeight();
    }
    
}
