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

    }

    protected void changeWeapon() {
        if (currentWeaponType == 'c')
            currentWeaponType = 'r';
        else
            currentWeaponType = 'c';
    }

    public char getCurrentWeaponType() {
        return currentWeaponType;
    }

    public int getCannonCount() {
        return cannonCount;
    }

    public boolean decreaseCannonCount() {
        cannonCount--;
        if (cannonCount < 0) {
            cannonCount = 0;
            return false;
        }
        return true;
    }

    public int getRifleCount() {
        return rifleCount;
    }

    public boolean decreaseRifleCount() {
        rifleCount--;
        if (rifleCount < 0) {
            rifleCount = 0;
            return false;
        }
        return true;
    }


}
