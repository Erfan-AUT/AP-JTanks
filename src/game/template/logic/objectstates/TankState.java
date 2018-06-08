/*** In The Name of Allah ***/
package game.template.logic.objectstates;

import game.sample.ball.GameFrame;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class TankState extends ObjectState {

    /**
     * The state of every tank, you'll notice the similarities between this and the original code in the template.
     * Added the rotating template to adjust the tank's cannon.
     */

    public boolean gameOver;
	protected boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    protected boolean rotating;
    protected boolean moving;
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
	 * The method which updates the tank's state.
	 */
	@Override
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

    public double getRotatingAngle() {
        return rotatingAngle;
    }

    public boolean isRotating() {
        return rotating;
    }

    public boolean isMoving() {
        return moving;
    }

    // At the end of every gameFrame rendering, this method should be called to revert to the original state
    // of not moving anything.
    public void revertToZeroState() {
        rotating = false;
        moving = false;
    }
}

