/*** In The Name of Allah ***/
package game.template.logic;

import game.sample.ball.GameFrame;

import java.awt.event.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public abstract class TankState extends ObjectState {

	public boolean gameOver;
	protected boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	//protected boolean mousePress;
	protected double rotatingAngle;
	//private int mouseX, mouseY;
	
	public TankState(int y, int x) {
	    super(y, x);
		diam = 32;
		gameOver = false;
		//
		keyUP = false;
		keyDOWN = false;
		keyRIGHT = false;
		keyLEFT = false;
		//
		//mousePress = false;
		//

	}
	
	/**
	 * The method which updates the game state.
	 */
	public void update() {
		if (keyUP)
			locY -= 8;
		if (keyDOWN)
			locY += 8;
		if (keyLEFT)
			locX -= 8;
		if (keyRIGHT)
		    locX += 8;

		locX = Math.max(locX, 0);
		locX = Math.min(locX, GameFrame.GAME_WIDTH - diam);
		locY = Math.max(locY, 0);
		locY = Math.min(locY, GameFrame.GAME_HEIGHT - diam);
	}
	
	

}

