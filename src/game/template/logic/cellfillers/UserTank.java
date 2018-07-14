package game.template.logic.cellfillers;

import game.template.bufferstrategy.GameState;
import game.template.graphics.Animation;
import game.template.logic.Map;
import game.template.logic.NetworkUser;
import game.template.logic.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserTank extends Tank {

    private int lives = 3;
    private int initialHealth;
    private transient BufferedImage[] cannons;
    private File cannonsLocation;
    private File[] cannonsImages;
    private transient BufferedImage[] rifles;
    private File riflesLocation;
    private File[] riflesImages;
    private transient BufferedImage lightBulletImage;
    private int rotationDegree;
    private boolean isOnCannon;
    private int currentRifle;
    private int currentCannon;
    private User user;
    private int primeVelocity = 50;

    /**
     * creates a new user tank using the given parameters.
     * @param y
     * @param y              its x-location
     * @param x              its y-location
     * @param health
     * @param whichMap       the map it is in
     * @param location       the location of its animation's content.
     */


    public UserTank(int y, int x, int health, Map whichMap, String location) {
        super(y, x, health, whichMap, location, ".\\Bullet\\HeavyBullet.png");
        initialHealth = health;
        //setVelocity(10);
        isOnCannon = true;
        cannons = new BufferedImage[4];
        cannonsLocation = new File(".\\PlayerCannons");
        cannonsImages = cannonsLocation.listFiles();
        rifles = new BufferedImage[3];
        riflesLocation = new File(".\\PlayersRifles");
        riflesImages = riflesLocation.listFiles();
        rotationDegree = 90;
        setVelocity(primeVelocity);
//        setCannonX(getX() + 75);
//        setCannonY(getY() + 75);
        // readContents(location);
        //animation = new Animation(tankImages, 250, 250, 4, 24, false, locX, locY, 0);
        ((Animation) animation).setGun(cannons[0]);
        currentRifle = 0;
        currentCannon = 0;
        //    readContents(location);
        for (int i = 0; i < cannonsImages.length; i++) {
            try {
                cannons[i] = ImageIO.read(cannonsImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < riflesImages.length; i++) {
            try {
                rifles[i] = ImageIO.read(riflesImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            lightBulletImage = ImageIO.read(new File("./Bullet/LightBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Animation) animation).setGun(cannons[currentCannon]);
    }

    public UserTank(int y, int x, int health, Map whichMap, String location, GameState state) {
        super(y, x, health, whichMap, location, ".\\Bullet\\HeavyBullet.png");
        initialHealth = health;
        setVelocity(primeVelocity);
    }

    /**
     * sets a new user for the tank.
     * @param user
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * loads the transient fields of the tank after it has been loaded from a
     * serialized file.
     */

    public void loadTransientFields()
    {
        cannons = new BufferedImage[4];
        rifles = new BufferedImage[3];
        for (int i = 0; i < cannonsImages.length; i++) {
            try {
                cannons[i] = ImageIO.read(cannonsImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < riflesImages.length; i++) {
            try {
                rifles[i] = ImageIO.read(riflesImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            lightBulletImage = ImageIO.read(new File("./Bullet/LightBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Animation) animation).setGun(cannons[currentCannon]);
    }

    /**
     * shoots a new bullet at a certain degree of rotation
     * @param deg the degree
     * @return the bullet.
     */

    @Override
    public Bullet shoot(double deg) {
        long time = System.currentTimeMillis();
        if (isOnCannon) {
            if (lastShootTime == 0 || time > lastShootTime + 1000) {
                return finalizeShoot(heavyBulletImage, deg);
            }
        } else {
            if (lastShootTime == 0 || time > lastShootTime + 100) {
                return finalizeShoot(lightBulletImage, deg);
            }
        }
        return null;
//        boolean check;
//        if (getCurrentWeaponType() == 'c')
//            check = decreaseCannonCount();
//        else
//            check = decreaseRifleCount();
//        if (check) {
//            //This should be changed to cannon's location.
//            String location;
//            if (getCurrentWeaponType() == 'r')
//                location = "\\images\\LightBullet.png";
//            else
//                location = "\\images\\HeavyBullet.png";
//            new Bullet(locY, locX,
//                    this.whichMap, getAngleInRadians(), getCurrentWeaponType(), location);
//        }
    }
    /**
     * takes damage from other components.
     *
     * @param damage
     */
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
    /**
     * moves the tank using its internal parameters.
     */
    @Override
    public void move() {
        boolean isMoving = false;
        if (user.isKeyUP()) {
            locY -= velocity;
            locY -= avoidCollision();
            isMoving = true;
        } else if (user.isKeyDOWN()) {
            locY += velocity;
            locY += avoidCollision();
            isMoving = true;
        }
        if (user.isKeyRIGHT()) {
            locX += velocity;
            locX += avoidCollision();
            isMoving = true;
        } else if (user.isKeyLEFT()) {
            locX -= velocity;
            locX -= avoidCollision();
            isMoving = true;
        }
//        setCannonX(getX() + 75);
//        setCannonY(getY() + 75);
        animation.changeCoordinates(locX, locY);
        if (isMoving) {
            ((Animation) animation).setActive(true);
        } else {
            ((Animation) animation).setActive(false);
        }
    }

    /**
     * rotates the tank using the user-driven degrees.
     */

    public void rotate() {
        int tmp = getAngle() % 360;
        int x = locX;
        int y = locY;
        boolean isMoving = false;
        if (tmp < 0) {
            setAngle(360 + tmp);
        } else {
            setAngle(tmp);
        }

        if (user.isKeyUP() && user.isKeyRIGHT() || user.isKeyRIGHT() && user.isKeyUP()) {
            if (getAngle() >= 180 && getAngle() < 305) {
                crossRot(5);
            } else if (getAngle() == 305) {
                setVelocity(primeVelocity);
            } else {
                crossRot(-5);
            }
        } else if (user.isKeyUP() && user.isKeyLEFT() || user.isKeyLEFT() && user.isKeyUP()) {
            if ((getAngle() <= 360 && getAngle() > 215) || getAngle() == 0) {
                crossRot(-5);
            } else if (getAngle() == 215) {
                setVelocity(primeVelocity);
            } else {
                crossRot(5);
            }
        } else if (user.isKeyDOWN() && user.isKeyLEFT()) {
            if (getAngle() >= 0 && getAngle() < 135) {
                crossRot(5);
            } else if (getAngle() == 135) {
                setVelocity(primeVelocity);
            } else {
                crossRot(-5);
            }
        } else if (user.isKeyDOWN() && user.isKeyRIGHT()) {
            if (getAngle() <= 180 && getAngle() > 45) {
                crossRot(-5);
            } else if (getAngle() == 45) {
                setVelocity(primeVelocity);
            } else {
                crossRot(5);
            }
        }
        if (user.isKeyRIGHT() && !(user.isKeyUP() || user.isKeyDOWN())) {
            if (getAngle() != 0 && getAngle() != 180) {
                if (getAngle() < 180) {
                    rot(-5);
                    // System.out.println(getAngle());
                } else {
                    rot(5);
                    //System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 0) {
                    setForward(true);
                    setVelocity(primeVelocity);
                    //System.out.println(getAngle());
                } else {
                    setForward(false);
                    setVelocity(primeVelocity);
                    // System.out.println(getAngle());
                }
            }
        } else if (user.isKeyLEFT() && !(user.isKeyUP() || user.isKeyDOWN())) {
            if (getAngle() != 0 && getAngle() != 180) {
                if (getAngle() < 180) {
                    rot(5);
                    // System.out.println(getAngle());
                } else {
                    rot(-5);
                    //  System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 0) {
                    setForward(false);
                    setVelocity(primeVelocity);
                    // System.out.println(getAngle());
                } else {
                    setForward(true);
                    setVelocity(primeVelocity);
                    //System.out.println(getAngle());
                }
            }
        } else if (user.isKeyUP() && !(user.isKeyRIGHT() || user.isKeyLEFT())) {
            if (getAngle() != 90 && getAngle() != 270) {
                if (getAngle() > 90 && getAngle() < 270) {
                    rot(5);
                    //   System.out.println(getAngle());
                } else {
                    rot(-5);
                    //System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 270) {
                    setForward(true);
                    setVelocity(primeVelocity);
                    // System.out.println(getAngle());
                } else {
                    setForward(false);
                    setVelocity(primeVelocity);
                    // System.out.println(getAngle());
                }
            }
        } else if (user.isKeyDOWN() && !(user.isKeyRIGHT() || user.isKeyLEFT())) {
            if (getAngle() != 90 && getAngle() != 270) {
                if (getAngle() > 90 && getAngle() < 270) {
                    rot(-5);
                    // System.out.println(getAngle());
                } else {
                    rot(5);
                    //System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 90) {
                    setForward(true);
                    setVelocity(primeVelocity);
                    // System.out.println(getAngle());
                } else {
                    setForward(false);
                    setVelocity(primeVelocity);
                    // System.out.println(getAngle());
                }
            }
        }
    }

    /**
     * does some particular type of rotation that's used widely in the code.
     * @param deg the degree of rotation.
     */

    private void rot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        ((Animation) animation).setMovingRotationDeg(getAngle());
    }

    /**
     * does some other particular type of rotation that's used widely in the code.
     * @param deg the degree of rotation.
     */
    private void crossRot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        ((Animation) animation).setMovingRotationDeg(getAngle());
    }

    /**
     * rotates the tank's cannon.
     * @return it degree after rotation.
     */

    private double rotateTheCannon() {
        int dx = user.getMouseX() - (locX + 10);
        int dy = user.getMouseY() - (locY + 20);
        double deg = Math.atan2(dy, dx);
        ((Animation) animation).setCannonRotationDeg(deg);
        return deg;
    }

    /**
     * updates the tank given its new state.
     */

    public void update() {

        if (user.isKeyUP() || user.isKeyDOWN() || user.isKeyLEFT() || user.isKeyRIGHT()) {
            rotate();
            move();
        } else {
            ((Animation) animation).setActive(false);
        }
        double deg = 0;
        if (user.isMouseMoved())
            deg = rotateTheCannon();
        if (user.isMouseLeftClickPressed()) {
            Bullet bullet = shoot(deg);
            if (bullet != null) {
                ((Animation) animation).getBullets().add(bullet);
            }
//            synchronized(whichMap) {
//                whichMap.getAllObjects().add(shoot(deg));
//            }
        }
        if (user.isMouseRightClickPressed())
            changeWeapon();

    }

    /**
     * recieves a gif block and adds it to itself.
     * @param gift the gift.
     */
    public void recieveGift(Block gift) {
        if (gift.getType() == "cf")
            increaseCannonCount(20);

        whichMap.getAllObjects().remove(gift);

    }

    /**
     * chnages its gun.
     */

    public void changeTheGun() {
        if (isOnCannon) {
            ((Animation) animation).setGun(cannons[currentCannon]);
        } else {
            ((Animation) animation).setGun(rifles[currentRifle]);
        }
    }

    public boolean isOnCannon() {
        return isOnCannon;
    }

    /**
     * sets a new state for its cannon.
     * @param onCannon the new state.
     */

    public void setOnCannon(boolean onCannon) {
        isOnCannon = onCannon;
    }

    /**
     * changes its weapon.
     */

    @Override
    protected void changeWeapon() {
        setOnCannon(!isOnCannon);
        changeTheGun();
    }

}
