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
public class FlappyBirdGame extends Canvas implements Runnable {

    private FlappyBird fb;
    private GameEngine e;
    
    public FlappyBirdGame(){
        fb = new FlappyBird();
    }
    
    protected void paint(Graphics g) {
        fb.onDraw(new GameCanvas(g, this));
    }

    public void run() {
        while(true){
            if (fb.engine == null){
                e = new GameEngine(this);
                fb.engine = e;
            }
            fb.onTick();
            repaint ();
        }
        
    }
    
}
