package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.ObjectState;
import game.template.logic.objectstates.TankState;

public abstract class Tank extends GameObject {

    // r for rifle, c for cannon.
    private char currentWeaponType;
    //Number of cannonballs it has left.
    private int cannonCount;
    private int rifleCount;

    public Tank(int health, Map whichMap) {
        super(true, health, whichMap);
        rifleCount = 300;
        cannonCount = 50;
    }

    protected void shoot() {
        boolean check = false;
        if (currentWeaponType == 'c') {
            if (cannonCount >= 1) {
                cannonCount--;
                check = true;
            }
        } else if (rifleCount >= 1) {
            rifleCount--;
            check = true;
        }
        if (check)
            new Bullet(state.locY, state.locX, 0,
                    this.whichMap, ((TankState) state).getRotatingAngle(), currentWeaponType);
    }

    protected void changeWeapon() {
        if (currentWeaponType == 'c')
            currentWeaponType = 'r';
        else
            currentWeaponType = 'c';
    }


}
