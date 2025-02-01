/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybird;

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
public class FlappyBirdGame extends Canvas implements Runnable {

    private FlappyBird fb;
    private GameEngine e;
    
    public FlappyBirdGame(){
        createAudioPlayer ();
        fb = new FlappyBird();


    }
    
    protected void paint(Graphics g) {
        fb.onDraw(new GameCanvas(g, this));
    }
    
    private Player audioPlayer;
    private Player tonePlayer;
    public void createAudioPlayer () {
        byte[] bkmelody =
            {
                ToneControl.VERSION, 1, ToneControl.SET_VOLUME, 40, 76, 8, 81, 24, 76, 8, 88, 24, 81,
                8, 85, 16, 83, 8, 85, 8, 81, 16, 76, 16, 81, 16, 90, 16, 88, 16, 85, 8, 86, 8, 88,
                48, ToneControl.SILENCE, 8, 76, 8, 81, 16, 90, 16, 88, 16, 85, 8, 86, 8, 88, 16, 83,
                8, 85, 8, 81, 16, 76, 16, 81, 16, 85, 8, 86, 8, 83, 24, 81, 8, 81, 32
            };

        byte[] tseq = {ToneControl.VERSION, 1, 64, 4, 65, 4, 66, 4};

        if (audioPlayer != null) {
            audioPlayer.close ();
            audioPlayer = null;

            try {
                Thread.sleep (200);
            }
            catch (Exception ex) {
            }
        }

        try {
            ToneControl tControl;

            tonePlayer = Manager.createPlayer (Manager.TONE_DEVICE_LOCATOR);
            tonePlayer.realize ();
            tControl = (ToneControl) tonePlayer.getControl ("ToneControl");
            tControl.setSequence (tseq);

            audioPlayer = Manager.createPlayer (Manager.TONE_DEVICE_LOCATOR);
            audioPlayer.setLoopCount (-1);
            audioPlayer.realize ();
            tControl = (ToneControl) audioPlayer.getControl ("ToneControl");
//            tControl.setSequence (bkmelody);
            audioPlayer.start ();
        }
        catch (Exception ex) {
            ex.printStackTrace ();
        }
    }
    
    public void keyPressed (int keyCode) {
        fb.keyPressed(keyCode);
        if (audioPlayer != null) {
             try {
                 Manager.playTone (78, 10, 20); // Play audio
             }
             catch (MediaException me) {
                 me.printStackTrace();
             }
         }
    }

    public void run() {
        while(true){
            try{
                synchronized (fb){
                    fb.onTick();
                    repaint ();
                    fb.wait (18);
                }
            
                } catch (Exception ie) {
                ie.printStackTrace();
            }
                
        }
        
    }
    
}
