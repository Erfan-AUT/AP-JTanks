package game.template.logic;

import java.awt.event.*;

public class UserTankState extends TankState {


    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private boolean rotating;
    private boolean moving;
    private boolean mousePress;


    public UserTankState(int locY, int locX) {
        super(locY, locX);
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
     * The mouse handler.
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
