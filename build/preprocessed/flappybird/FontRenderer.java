/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;

/**
 *
 * @author nocrail
 */
public class FontRenderer {

    final int textScale;

    FontRenderer(int textScale) {
        this.textScale = textScale;
    }

    private void drawBorder(Game.Canvas canvas, int x, int y) {
        canvas.setColor(0, 0, 0, 255);
        canvas.drawRect(x - 1, y - 1, textScale * 4 + 2, textScale * 5 + 2);
        canvas.setColor(255, 255, 255, 255);
    }

    void draw0(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x, y, textScale, textScale * 5);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }

    void draw1(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
    }

    void draw2(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 3);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
        canvas.drawRect(x, y + textScale * 2, textScale, textScale * 3);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }

    void draw3(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }

    void draw4(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale, textScale * 3);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
    }

    void draw5(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x, y, textScale, textScale * 3);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
        canvas.drawRect(x + textScale * 3, y + textScale * 2, textScale, textScale * 3);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }

    void draw6(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
        canvas.drawRect(x + textScale * 3, y + textScale * 2, textScale, textScale * 3);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }

    void draw7(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
        canvas.drawRect(x, y, textScale * 4, textScale);
    }

    void draw8(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }

    void draw9(Game.Canvas canvas, int x, int y) {
        drawBorder(canvas, x, y);
        canvas.drawRect(x, y, textScale * 4, textScale);
        canvas.drawRect(x, y, textScale, textScale * 3);
        canvas.drawRect(x, y + textScale * 2, textScale * 4, textScale);
        canvas.drawRect(x + textScale * 3, y, textScale, textScale * 5);
        canvas.drawRect(x, y + textScale * 4, textScale * 4, textScale);
    }
}
