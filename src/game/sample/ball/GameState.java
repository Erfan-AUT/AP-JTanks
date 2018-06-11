/*** In The Name of Allah ***/
package game.sample.ball;

import game.template.logic.Map;
import game.template.logic.cellfillers.Bullet;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.Tank;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {
	
	public int locX, locY, diam;
	public boolean gameOver;
	
	private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	private boolean mousePress;
	private int mouseX, mouseY;	
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private Map map;

	
	public GameState() {
		locX = 100;
		locY = 100;
		diam = 32;
		gameOver = false;
		//
		keyUP = false;
		keyDOWN = false;
		keyRIGHT = false;
		keyLEFT = false;
		//
		mousePress = false;
		mouseX = 0;
		mouseY = 0;
		//
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();

		map = new Map(1);
	}
	
	/**
	 * The method which updates the game state.
	 */
	public void update() {
		if (mousePress) {
			locY = mouseY - diam / 2;
			locX = mouseX - diam / 2;
		}
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

        /**
         * cross-checks all bullets with all destructible objects to see whether or not they collide.
         */

        for (GameObject bullet : map.getVisibleObjects()) {
            if (bullet instanceof Bullet) {
                boolean collision;
                for (GameObject target : map.getVisibleObjects() ) {
                    if (target.isDestructible()) {
                        if (collision = checkIfTwoObjectsCollide(bullet, target)) {
                            target.takeDamage(((Bullet) bullet).getDamage());
                            break;
                        }
                    }
                }
            }
        }
	}
	
	
	public KeyListener getKeyListener() {
		return keyHandler;
	}
	public MouseListener getMouseListener() {
		return mouseHandler;
	}
	public MouseMotionListener getMouseMotionListener() {
		return mouseHandler;
	}
	public Map getMap() { return map; }

	public static boolean checkIfTwoObjectsCollide(GameObject one, GameObject two)
    {
        int deltaX = one.getState().locX - two.getState().locX;
        int deltaY = one.getState().locY - two.getState().locY;
        //double distance = Math.pow(deltaX + deltaY, 0.5);
        //double angle = Math.atan(deltaY / deltaX);
        if ((0 >= deltaY - one.getHeight() - two.getHeight()) && (0 >= deltaX - one.getWidth() - two.getWidth()))
            return true;
        return false;
    }




	/**
	 * The keyboard handler.
	 */
	class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					keyUP = true;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = true;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = true;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = true;
					break;
				case KeyEvent.VK_ESCAPE:
					gameOver = true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					keyUP = false;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = false;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = false;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = false;
					break;
			}
		}

	}

	/**
	 * The mouse handler.
	 */
	class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			mousePress = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mousePress = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}
}

