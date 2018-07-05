package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.TankState;
import game.template.logic.objectstates.UserTankState;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTank extends Tank {

    public UserTank(int y, int x, int health, Map whichMap) {
        super(health, whichMap);
        state = new UserTankState(y, x, this);
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

    @Override
    protected void shoot() {
        boolean check = false;
        if (getCurrentWeaponType() == 'c')
            check = decreaseCannonCount();
        else
            check = decreaseRifleCount();
        if (check)
            new Bullet(state.locY, state.locX,
                    this.whichMap, ((UserTankState) state).getRotatingAngle(), getCurrentWeaponType());
    }
}
