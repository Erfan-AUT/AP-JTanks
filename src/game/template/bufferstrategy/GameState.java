/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.logic.Map;
import game.template.logic.cellfillers.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

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
    private boolean mouseMoved;


    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Map map;
    private Tank playerTank;

    public Tank getPlayerTank() {
        return playerTank;
    }

    public static boolean gameOver;


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
        //playerTank = new UserTank(50, 50, 100, null, ".\\Move\\Tank", this);
//        playerTank = map.getMainTank();
    }

    public void setMap(Map map) {
        this.map = map;
        playerTank = map.getMainTank();
    }

    /**
     * The method which updates the game state.
     */
    public void update() {

//        map.getMainTank().update();
//        for (GameObject bullet : map.getVisibleObjects()) {
//            for (GameObject target : map.getVisibleObjects()) {
//                if (target.isDestructible()) {
//                    if ((bullet instanceof Bullet) ||
//                            ((target instanceof UserTank) && (bullet instanceof ComputerTank)
//                                    && ((ComputerTank) bullet).isDoesCollisionDamageUserTank())) {
//                        if (checkIfTwoObjectsCollide(bullet, target)) {
//                            target.takeDamage(bullet.getDamage());
//                            break;
//                        }
//                    }
//                }
//            }
//        }
       for (GameObject object : map.getAllObjects())
            object.update();
       map.update();
       //map.update();
        //playerTank.update();
        //
        // Update the state of all game elements
        //  based on user input and elapsed time ...
        //
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

    public Map getMap() {
        return map;
    }

    public static boolean checkIfTwoObjectsCollide(GameObject one, GameObject two) {
        int deltaX = Math.abs(one.locX - two.locX);
        int deltaY = Math.abs(one.locY - two.locY);
        //Soon to be replaced when the rotating angle is considered.
        Dimension d1 = getRelativeHeightWidth(one), d2 = getRelativeHeightWidth(two);
        int height1 = d1.height;
        int width1 = d1.width;
        int height2 = d2.height;
        int width2 = d2.width;
        System.out.println("Y dif:");
        System.out.println(deltaY - height1 - height2);
        System.out.println("X dif:");
        System.out.println(deltaX - width1 - width2);
        if ((0 >= deltaY - height1 - height2) && (0 >= deltaX - width1 - width2))
            return true;
        return false;
    }

    public static Dimension getRelativeHeightWidth(GameObject object) {
        Dimension d = new Dimension();
        double angle = object.getAngleInRadians();
        d.height = (int) Math.abs((object.getHeight() * Math.sin(angle) + object.getWidth() * Math.cos(angle)));
        d.width = (int) Math.abs((object.getHeight() * Math.cos(angle) + object.getWidth() * Math.sin(angle)));
        return d;
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
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseMoved = true;
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

    public boolean isMouseMoved() {
        return mouseMoved;
    }

}

