package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.UserTankState;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTank extends Tank {

    public UserTank(int y, int x, boolean isDestructible, int health, Map whichMap) {
        super(isDestructible, health, whichMap);
        state = new UserTankState(y, x);
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
