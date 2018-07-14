package game.template.logic.cellfillers;

import game.template.bufferstrategy.GameState;
import game.template.graphics.Animation;
import game.template.logic.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private File bulletLocation;
    protected long lastShootTime;
    protected transient BufferedImage heavyBulletImage;
    //protected boolean mousePress;
    //private int mouseX, mouseY;


    /**
     * creates a new tank using the given parameters.
     *
     * @param y
     * @param y              its x-location
     * @param x              its y-location
     * @param health         its health
     * @param whichMap       the map it is in
     * @param location       the location of its animation's content.
     * @param bulletLocation the location of its bullet's animation's content.
     */

    public Tank(int y, int x, int health, Map whichMap, String location, String bulletLocation) {
        super(y, x, true, health, whichMap, location);
        this.bulletLocation = new File(bulletLocation);
        try {
            heavyBulletImage = ImageIO.read(new File(bulletLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rifleCount = 300;
        cannonCount = 50;
        forward = false;
        angle = 0;
        animation = new Animation(images, 150, 150, 4, 20, false, x, y, 0);
        animation.active = true;
        lastShootTime = 0;
    }

    public Tank(int y, int x, int health, Map whichMap, String location) {
        super(y, x, true, health, whichMap, location);
    }

    /**
     * Avoids collision with other non-passable object when moving.
     *
     * @return how much it needs to go back.
     */

    public int avoidCollision() {
        if (!whichMap.doesntGoOutOfMap(this, false, 0))
            return -velocity;
//        if (this instanceof UserTank)
//            if ((locX / 120 <= 0) || (locY / 120 <= 20))
//                return -velocity;
//        if (this instanceof UserTank)
//            if (!whichMap.doesntGoOutOfMap(this, true))
//                return -velocity;
        for (GameObject object : whichMap.getVisibleObjects()) {
            if (object != this) {
                if (GameState.checkIfTwoObjectsCollide(object, this)) {
                    if (object instanceof Block) {
                        if (!((Block) object).isPassableByTank()) {
                            //  System.out.println("Collides with object.");
                            return -velocity;
                        }
                    } else if (object instanceof ComputerTank)
                        return -velocity;
                }
            }
        }
        return 0;
    }

    /**
     * loads the transient fields of the tank after it has been loaded from a
     * serialized file.
     */

    public abstract void loadTransientFields();

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


    /**
     * shoots a new bullet at a certain degree of rotation
     *
     * @param deg the degree
     * @return the bullet.
     */
    protected abstract Bullet shoot(double deg);

    /**
     * moves the tank using its internal parameters.
     */
    protected abstract void move();

    /**
     * changes the tank's weapon.
     */
    protected void changeWeapon() {

//        if (currentWeaponType == 'c')
//            currentWeaponType = 'r';
//        else
//            currentWeaponType = 'c';
    }

    public char getCurrentWeaponType() {
        return currentWeaponType;
    }

    public int getCannonCount() {
        return cannonCount;
    }

    /**
     * decreases cannon's count after a shot
     *
     * @return false if it becomes zero
     */
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

    /**
     * decreases rifle's count after a shot
     *
     * @return false if it becomes zero
     */
    public boolean decreaseRifleCount() {
        rifleCount--;
        if (rifleCount < 0) {
            rifleCount = 0;
            return false;
        }
        return true;
    }

    /**
     * @return its velocity.
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * sets a new veloctiy for the tank.
     *
     * @param velocity the new velocity.
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * @return whether or not it is moving forward.
     */

    public boolean isForward() {
        return forward;
    }

    /**
     * sets its moving direction.
     *
     * @param forward the direction.
     */

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    /**
     * @return its rotating angle.
     */

    public int getAngle() {
        return angle;
    }

    /**
     * sets a new angle of rotation for it.
     *
     * @param angle the angle.
     */

    public void setAngle(int angle) {
        this.angle = angle;
    }

    /**
     * sets whether or not its moving.
     *
     * @param moving the state.
     */

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * reads the content necessary for the object's animation.
     *
     * @param location where to read the info from.
     */

    @Override
    public void readContents(String location) {
        super.readContents(location);
        try {
            heavyBulletImage = ImageIO.read(new File(".\\Bullet\\HeavyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * does the  common things that need to be done in case a bullet is shot.
     *
     * @param image the image of the bullet, manifesting its type.
     * @param deg   the degree at which the bullet is shot.
     * @return the bullet that is shot.
     */

    protected Bullet finalizeShoot(BufferedImage image, double deg) {
        long time = System.currentTimeMillis();
        lastShootTime = time;
        Bullet bullet;
        int x = (int) (locX + 120 + Math.cos(deg) * 120);
        int y = (int) (locY + 75 + Math.sin(deg) * (120));
        bullet = new Bullet(image, x,
                y, Math.cos(deg), Math.sin(deg), deg, whichMap, 40);
        Thread thread = new Thread(bullet);
        thread.start();
        whichMap.getBullets().add(bullet);
        return bullet;
    }

    /**
     * increases the count of remaining cannons for the tank.
     *
     * @param plus
     */

    protected void increaseCannonCount(int plus) {
        cannonCount += plus;
    }


    /**
     * increases the count of remaining rifles for the tank.
     *
     * @param plus
     */

    protected void increaseRifleCount(int plus) {
        rifleCount += plus;
    }

    /**
     * creates its animation and puts it in the proper state.
     */

    @Override
    public void displayTheAnimations() {
        animation = new Animation(images, 150, 150, 4, 20, false, locX, locY, 0);
        animation.active = true;
    }
}
