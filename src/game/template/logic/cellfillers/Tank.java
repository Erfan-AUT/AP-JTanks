package game.template.logic.cellfillers;

import game.template.bufferstrategy.GameState;
import game.template.graphics.Animation;
import game.template.logic.Map;

import java.awt.image.BufferedImage;

public abstract class Tank extends GameObject {

    // r for rifle, c for cannon.
    private char currentWeaponType;
    protected Animation explosion;
    private boolean forward;
    //Number of cannonballs it has left.
    private int cannonCount;
    private int rifleCount;
    protected boolean rotating;
    protected boolean moving;
    //protected boolean mousePress;
    //private int mouseX, mouseY;


    public Tank(int y, int x, int health, Map whichMap, String location) {
        super(y, x, true, health, whichMap, location);
        rifleCount = 300;
        cannonCount = 50;
        forward = false;
        angle = 0;
    }

    public int avoidCollision() {
        for (GameObject object : whichMap.getVisibleObjects()) {
            if (object != this) {
                if (GameState.checkIfTwoObjectsCollide(object, this)) {
                    if (object instanceof Block) {
                        if (!((Block) object).isPassableByTank())
                            return -velocity;
                    } else
                        return -velocity;
                } else if (!whichMap.doesntGoOutOfMap(this, false))
                    return -velocity;
            }
        }
        return 0;
    }

    public boolean isRotating() {
        return rotating;
    }

    public boolean isMoving() {
        return moving;
    }

    // At the end of every gameFrame rendering, this method should be called to revert to the original state
    // of not moving anything.
    public void revertToZeroState() {
        rotating = false;
        moving = false;
    }


    protected abstract void shoot();

    protected abstract void move();


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

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

}
