package game.template.logic.objectstates;

import game.sample.ball.GameFrame;
import game.template.logic.cellfillers.UserTank;

import java.awt.event.*;

/**
 * The state class belonging to the tanks controlled by user.
 * Basically everything is a copy of Ghaffarian's code.
 * The only noticeable thing is the rotational angle.
 */

public class UserTankState extends TankState {


    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    //Does it rotate?

    private boolean mousePress;


    public UserTankState(int locY, int locX, UserTank source) {
        super(locY, locX, source);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        mousePress = false;
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


    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    moving = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    moving = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    moving = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    moving = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameOver = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
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
     * The method which updates the tank's state.
     */
    @Override
    public void update() {
        if (keyUP) {
            locY -= 8;
            locY -= avoidCollision();
        }
        if (keyDOWN) {
            locY += 8 ;
            locY += avoidCollision();
        }
        if (keyLEFT) {
            locX -= 8;
            locX -= avoidCollision();
        }
        if (keyRIGHT) {
            locX += 8;
            locX -= avoidCollision();
        }

        locX = Math.max(locX, 0);
        locX = Math.min(locX, GameFrame.GAME_WIDTH - source.getWidth());
        locY = Math.max(locY, 0);
        locY = Math.min(locY, GameFrame.GAME_HEIGHT - source.getHeight());
    }

    public double getRotatingAngle() {
        return rotatingAngle;
    }

    /**
     * The mouse handler.
     * Tries to find the angle between the line connecting the tank and the mouse.
     */
    class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            rotating = true;
            rotatingAngle = Math.atan(e.getY() - locY / e.getX() - locX);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePress = true;
        }
    }


}
