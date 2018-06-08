package game.template.logic.objectstates;

import game.template.graphics.Animation;

/**
 * The state of every bullet in the game.
 * Could be a cannonball or a rifle bullet.
 * Has a shooting angle to cover the map.
 * Notice that we either have to make our map so big in size that the user can't see the difference between blocks
 * and thinks its a smooth transition when bullets and tanks collide,
 * or we could check whether or not the bullet is going to hit something at every point.
 * Both are shitty solutions, If you have a better one, I beg you to inform me about it. :))
 */
public class BulletState extends ObjectState {

    private double shootingAngle;
    /**
     * The speed at which the bullet is going to pass through the map.
     */
    private int mainSpeed;
    private Animation animation;

    public BulletState(int locY, int locX, double shootingAngle) {
        super(locY, locX);
        this.shootingAngle = shootingAngle;
        mainSpeed = 8;
    }

    /**
     * Shoots the bullet in the specified direction.
     */
    @Override
    public void update() {
        locX += mainSpeed * Math.cos(shootingAngle);
        locY += mainSpeed * Math.sin(shootingAngle);
    }
}
