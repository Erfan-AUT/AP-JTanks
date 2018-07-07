package game.template.logic.cellfillers;

import game.sample.ball.GameLoop;
import game.sample.ball.GameState;
import game.template.logic.Map;
import game.template.logic.objectstates.TankState;
import game.template.logic.objectstates.UserTankState;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTank extends Tank {

    private int lives = 3;
    private int initialHealth;

    public UserTank(int y, int x, int health, Map whichMap) {
        super(health, whichMap);
        initialHealth = health;
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
    public void shoot() {
        boolean check;
        if (getCurrentWeaponType() == 'c')
            check = decreaseCannonCount();
        else
            check = decreaseRifleCount();
        if (check)
            new Bullet(state.locY, state.locX,
                    this.whichMap, ((UserTankState) state).getRotatingAngle(), getCurrentWeaponType());
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (getHealth() < 0) {
            lives--;
            setHealth(initialHealth);
        }
        if (lives == 0) {
            setAlive(false);
            GameState.gameOver = true;
        }
    }
}
