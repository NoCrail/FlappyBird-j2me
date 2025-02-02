/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.impl;

import game.Game;

/**
 *
 * @author nocrail
 */
public class EngineImpl implements Game.SoundEngine {

    private javax.microedition.lcdui.Canvas c;

    public EngineImpl(javax.microedition.lcdui.Canvas c) {
        this.c = c;
    }

    public void soundJump() {
    }

    public void soundGameOver() {
    }

}
