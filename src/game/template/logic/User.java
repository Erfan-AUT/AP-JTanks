package game.template.logic;

import game.template.bufferstrategy.GameState;
import game.template.logic.cellfillers.UserTank;

import java.awt.event.*;
import java.io.Serializable;

public class User implements Serializable{
    protected Map map;
    protected UserTank tank;
    protected boolean trueForServerFalseForClient;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mouseRightClickPressed;
    private boolean mouseLeftClickPressed;
    private boolean mouseMoved;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private int number;

    public User(Map map, boolean trueForServerFalseForClient) {
        this.map = map;
        if (trueForServerFalseForClient)
            tank = map.getMainTanks().get(0);
        else
            tank = map.getMainTanks().get(1);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        if (trueForServerFalseForClient)
            number = 0;
        else
            number = 1;
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

    public boolean isMouseMoved() {
        return mouseMoved;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    class KeyHandler implements KeyListener, Serializable {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            tank.setMoving(true);
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
            tank.setMoving(false);
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
    class MouseHandler implements MouseListener, MouseMotionListener, Serializable {

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
            mouseX = e.getX() + map.getCameraZeroX(number) - 40;
            mouseY = e.getY() + map.getCameraZeroY(number) - 40 ;
            mouseMoved = true;
        }
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public void setKeyUP(boolean keyUP) {
        this.keyUP = keyUP;
    }

    public void setKeyDOWN(boolean keyDOWN) {
        this.keyDOWN = keyDOWN;
    }

    public void setKeyRIGHT(boolean keyRIGHT) {
        this.keyRIGHT = keyRIGHT;
    }

    public void setKeyLEFT(boolean keyLEFT) {
        this.keyLEFT = keyLEFT;
    }

    public void setMouseRightClickPressed(boolean mouseRightClickPressed) {
        this.mouseRightClickPressed = mouseRightClickPressed;
    }

    public void setMouseLeftClickPressed(boolean mouseLeftClickPressed) {
        this.mouseLeftClickPressed = mouseLeftClickPressed;
    }

    public void setMouseMoved(boolean mouseMoved) {
        this.mouseMoved = mouseMoved;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getNumber() {
        return number;
    }
}
