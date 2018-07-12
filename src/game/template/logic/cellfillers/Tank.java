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
    protected BufferedImage[] tankImages;
    protected BufferedImage heavyBulletImage;
    //protected boolean mousePress;
    //private int mouseX, mouseY;


    public Tank(int y, int x, int health, Map whichMap, String location, String bulletLocation) {
        super(y, x, true, health, whichMap, location);
        this.bulletLocation = new File(bulletLocation);
        rifleCount = 300;
        cannonCount = 50;
        forward = false;
        angle = 0;
        animation = new Animation(images, 150, 150, 4, 20, false, x, y, 0);
        animation.active = true;
        lastShootTime = 0;
    }

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


    protected abstract Bullet shoot(double deg);

    protected abstract void move();


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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void readContents(String location) {
        super.readContents(location);
        try {
            heavyBulletImage = ImageIO.read(new File(".\\Bullet\\HeavyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Bullet finalizeShoot(BufferedImage image, double deg )
    {
        long time = System.currentTimeMillis();
        lastShootTime = time;
        Bullet bullet;
        int x =  (int) (locX + 67 + Math.cos(deg) * 100);
        int y = (int) (locY + 75 + Math.sin(deg) * (100));
        bullet = new Bullet(image, x,
              y, Math.cos(deg), Math.sin(deg), deg, whichMap, 40);
        Thread thread = new Thread(bullet);
        thread.start();
        whichMap.getBullets().add(bullet);
        return bullet;
    }

    protected void increaseCannonCount(int plus)
    {
        cannonCount += plus;
    }

    protected void increaseRifleCount(int plus)
    {
        rifleCount += plus;
    }
}
