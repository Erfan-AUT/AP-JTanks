package game.template.logic;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTank extends Tank {

    public UserTank(int y, int x, boolean isDestructible, int health, Map whichMap) {
        super(isDestructible, health, whichMap);
        tankState = new UserTankState(y, x);
    }

    private class MouseObserver extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e))
                changeWeapon();
            if (SwingUtilities.isLeftMouseButton(e))
                shoot();
        }
    }



}
