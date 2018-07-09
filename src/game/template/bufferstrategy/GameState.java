/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.Tanks.PlayerTank;
import game.template.Tanks.Tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class holds the state of the game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mouseRightClickPressed;
    private boolean mouseLeftClickPressed;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Tank playerTank = new PlayerTank(this);

    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        mouseRightClickPressed = false;
        mouseLeftClickPressed = false;
        mouseX = 0;
        mouseY = 0;
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        //
        // Update the state of all game elements
        //  based on user input and elapsed time ...
        //
        playerTank.update();
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
    class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    keyUP = true;
                    break;
                case KeyEvent.VK_D:
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_A:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_S:
                    keyDOWN = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    keyUP = false;
                    break;
                case KeyEvent.VK_D:
                    keyRIGHT = false;
                    break;
                case KeyEvent.VK_A:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_S:
                    keyDOWN = false;
                    break;
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
//            System.out.println(e.getX());
//            System.out.println(e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                mouseLeftClickPressed = true;
            } else if (e.getButton() == 3) {
                mouseRightClickPressed = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == 1) {
                mouseLeftClickPressed = false;
            } else if (e.getButton() == 3) {
                mouseRightClickPressed = false;
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = (int) e.getPoint().getX();
            mouseY = (int) e.getPoint().getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = (int) e.getPoint().getX();
            mouseY = (int) e.getPoint().getY();
        }
    }

    public boolean isKeyUP() {
        return keyUP;
    }

    public boolean isKeyDOWN() {
        return keyDOWN;
    }

    public boolean isKeyRIGHT() {
        return keyRIGHT;
    }

    public boolean isKeyLEFT() {
        return keyLEFT;
    }

    public boolean isMouseRightClickPressed() {
        return mouseRightClickPressed;
    }

    public boolean isMouseLeftClickPressed() {
        return mouseLeftClickPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }
}

