/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.internal;

import game.Game;
import game.impl.EngineImpl;
import game.impl.CanvasImpl;
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

    public void keyReleased(int keyCode) {
        game.keyReleased(keyCode);
    }

    public void keyRepeated(int keyCode) {
        game.keyRepeated(keyCode);
    }

    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    game.onTick();
                    repaint();
                    lock.wait(20);
                }

            } catch (Exception ie) {
                ie.printStackTrace();
            }
        }
    }
}
