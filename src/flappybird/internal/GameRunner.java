/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird.internal;

import flappybird.Game;
import flappybird.impl.EngineImpl;
import flappybird.impl.CanvasImpl;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.ToneControl;

/**
 *
 * @author nocrail
 */
public class GameRunner extends Canvas implements Runnable {

    private Game game;
    private Object lock;

    public GameRunner(Game game) {
        this.game = game;
        lock = new Object();
    }

    protected void paint(Graphics g) {
        game.onDraw(new CanvasImpl(g, this));
    }

    public void keyPressed(int keyCode) {
        game.keyPressed(keyCode);
    }

    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    game.onTick();
                    repaint();
                    lock.wait(18);
                }

            } catch (Exception ie) {
                ie.printStackTrace();
            }
        }
    }
}
