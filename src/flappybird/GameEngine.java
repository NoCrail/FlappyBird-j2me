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
public class GameEngine implements Game.Engine{
    
    private javax.microedition.lcdui.Canvas c;
    
    public GameEngine(javax.microedition.lcdui.Canvas c){
        this.c = c;
    }


    public void soundJump() {
    }

    public void soundGameOver() {
    }
    
}
