/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author nocrail
 */
public class Midlet extends MIDlet {
    FlappyBirdGame theGame = new FlappyBirdGame ();
    private Thread myThread;
    public void startApp() {
         
        Display.getDisplay (this).setCurrent (theGame);
        
        try {
            // Start the game in its own thread
            myThread = new Thread (theGame);
            //ensure the game thread will work after pause
//            theGame.setDestroyed (false);
            myThread.start ();
        }
        catch (Error e) {
            destroyApp (false);
            notifyDestroyed ();
        }
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        Display.getDisplay (this).setCurrent ((Displayable) null);
    }
}
