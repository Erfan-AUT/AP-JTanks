/*** In The Name of Allah ***/
package game.template.logic.objectstates;

import game.sample.ball.GameFrame;
import game.sample.ball.GameState;
import game.template.logic.cellfillers.Block;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.Tank;

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
    protected Tank source;
	//protected boolean mousePress;
	protected double rotatingAngle;
	//private int mouseX, mouseY;
	
	public TankState(int y, int x, Tank source) {
	    super(y, x);
		//diam = 32;
		gameOver = false;
		//
		keyUP = false;
		keyDOWN = false;
		keyRIGHT = false;
		keyLEFT = false;
		//
		//mousePress = false;
		//
        this.source = source;

	}
	


	public int avoidCollision()
    {
        for (GameObject object : source.getWhichMap().getVisibleObjects()) {
            if (object != source) {
                if (GameState.checkIfTwoObjectsCollide(object, source)) {
                    if (object instanceof Block) {
                        if (((Block) object).isPassableByTank())
                            return -8;
                    }
                    else
                        return -8;
                }
            }
        }
        return 0;
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

