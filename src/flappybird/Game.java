/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;

public interface Game {

    void onDraw(Canvas canvas);

    void onTick();

    public interface Engine {

        boolean isClick();

        void soundJump();

        void soundGameOver();
    }

    interface Canvas {
        /**
         * @param r - Red component [0..255]
         * @param g - Green component [0..255]
         * @param b - Blue component [0..255]
         * @param a - Alpha component [0..255]
         */
        void setColor(int r, int g, int b, int a);

        void drawRect(int x, int y, int w, int h);

        void drawText(int x, int y, String text);

        int getWidth();

        int getHeight();
    }
}
