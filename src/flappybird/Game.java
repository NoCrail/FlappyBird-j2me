/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;

public interface Game {

    public void onDraw(Canvas canvas);

    public void onTick();

    public interface Engine {


        public void soundJump();

        public void soundGameOver();
    }

    public interface Canvas {
        /**
         * @param r - Red component [0..255]
         * @param g - Green component [0..255]
         * @param b - Blue component [0..255]
         * @param a - Alpha component [0..255]
         */
        public void setColor(int r, int g, int b, int a);

        public void drawRect(int x, int y, int w, int h);
        
        public void drawImage(Object imageData, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest);

        public void drawText(int x, int y, String text);

        public int getWidth();

        public int getHeight();
    }
}