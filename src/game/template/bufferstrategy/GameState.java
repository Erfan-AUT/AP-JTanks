/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.logic.Map;
import game.template.logic.cellfillers.Bullet;
import game.template.logic.cellfillers.ComputerTank;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.UserTank;

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

    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Map map;
    public static boolean gameOver;


    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        //map = new Map(1);
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        map.getMainTank().update();
        for (GameObject bullet : map.getVisibleObjects()) {
            for (GameObject target : map.getVisibleObjects()) {
                if (target.isDestructible()) {
                    if ((bullet instanceof Bullet) ||
                            ((target instanceof UserTank) && (bullet instanceof ComputerTank)
                                    && ((ComputerTank) bullet).isDoesCollisionDamageUserTank())) {
                        if (checkIfTwoObjectsCollide(bullet, target)) {
                            target.takeDamage(bullet.getDamage());
                            break;
                        }
                    }
                }
            }
        }

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

        if ((0 >= deltaY - height1 - height2) && (0 >= deltaX - width1 - width2))
            return true;
        return false;
    }

    public static Dimension getRelativeHeightWidth(GameObject object) {
        Dimension d = new Dimension();
        d.height = (int) Math.abs((object.getHeight() + object.getWidth()) * Math.sin(object.getAngleInRadians()));
        d.width = (int) Math.abs((object.getHeight() + object.getWidth()) * Math.cos(object.getAngleInRadians()));
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
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
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
        }
    }
}

