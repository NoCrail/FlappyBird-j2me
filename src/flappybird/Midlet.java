/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import flappybird.internal.GameRunner;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author nocrail
 */
public class Midlet extends MIDlet {

    private GameRunner gameRunner;
    private Thread myThread;

    public void startApp() {
        gameRunner = new GameRunner(new FlappyBird());
        Display.getDisplay(this).setCurrent(gameRunner);

        try {
            // Start the game in its own thread
            myThread = new Thread(gameRunner);
            //ensure the game thread will work after pause
//            theGame.setDestroyed (false);
            myThread.start();
        } catch (Error e) {
            destroyApp(false);
            notifyDestroyed();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        Display.getDisplay(this).setCurrent((Displayable) null);
    }
}
