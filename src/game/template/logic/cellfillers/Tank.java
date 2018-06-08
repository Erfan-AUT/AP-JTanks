package game.template.logic;

import java.awt.*;

public abstract class Tank extends GameObject {

    // r for rifle, c for cannon.
    private char currentWeaponType;
    private int cannonCount;
    private int rifleCount;
    protected TankState tankState;

    public Tank(boolean isDestructible, int health, Map whichMap) {
        super(isDestructible, health, whichMap);
        rifleCount = 300;
        cannonCount = 50;
    }

    protected void shoot() {
        if (currentWeaponType == 'c') {
            if (cannonCount >= 1)
                cannonCount--;
        } else if (rifleCount >= 1)
            rifleCount--;
    }

    protected void changeWeapon() {
        if (currentWeaponType == 'c')
            currentWeaponType = 'r';
        else
            currentWeaponType = 'c';
    }

    public TankState getTankState() {
        return tankState;
    }


}
