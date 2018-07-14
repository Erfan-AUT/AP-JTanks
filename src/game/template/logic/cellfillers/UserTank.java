package game.template.logic.cellfillers;

import game.template.bufferstrategy.GameState;
import game.template.bufferstrategy.ThreadPool;
import game.template.graphics.Animation;
import game.template.logic.Map;
import game.template.logic.NetworkUser;
import game.template.logic.User;
import game.template.logic.utils.Music;

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

    public UserTank(int y, int x, int health, Map whichMap, String location) {
        super(y, x, health, whichMap, location, "." + File.separator
                + "Bullet" + File.separator
                + "HeavyBullet.png");
        initialHealth = health;
        //setVelocity(10);
        isOnCannon = true;
        cannons = new BufferedImage[4];
        cannonsLocation = new File("." + File.separator
                + "PlayerCannons");
        cannonsImages = cannonsLocation.listFiles();
        rifles = new BufferedImage[3];
        riflesLocation = new File("." + File.separator
                + "PlayersRifles");
        riflesImages = riflesLocation.listFiles();
        rotationDegree = 90;
        setVelocity(primeVelocity);
        ((Animation) animation).setGun(cannons[0]);
        currentRifle = 0;
        currentCannon = 0;
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
            lightBulletImage = ImageIO.read(new File("." + File.separator + "Bullet" + File.separator + "LightBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Animation) animation).setGun(cannons[currentCannon]);
    }

    public UserTank(int y, int x, int health, Map whichMap, String location, GameState state) {
        super(y, x, health, whichMap, location, "." + File.separator
                + "Bullet" + File.separator
                + "HeavyBullet.png");
        initialHealth = health;
        setVelocity(primeVelocity);
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * loads the transient fields of the tank after it has been loaded from a
     * serialized file.
     */
    public void loadTransientFields() {
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
            lightBulletImage = ImageIO.read(new File("." + File.separator + "Bullet"  + File.separator + "LightBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Animation) animation).setGun(cannons[currentCannon]);
    }


    /**
     * shoots a new bullet at a certain degree of rotation
     *
     * @param deg the degree
     * @return the bullet.
     */
    @Override
    public Bullet shoot(double deg) {
        long time = System.currentTimeMillis();
        if (isOnCannon) {
            if (lastShootTime == 0 || time > lastShootTime + 1000) {
                //decreaseCannonCount();
                if (decreaseCannonCount()) {
                    Music music = new Music("." + File.separator
                            + "Sounds" + File.separator
                            + "heavygun.mp3");
                    ThreadPool.execute(music);
                    return finalizeShoot(heavyBulletImage, deg);
                } else {
                    Music music = new Music("." + File.separator
                            + "Sounds" + File.separator
                            + "emptyGun.mp3");
                    ThreadPool.execute(music);
                }
            }
        } else {
            if (lastShootTime == 0 || time > lastShootTime + 100) {
                //decreaseRifleCount();
                if (decreaseRifleCount()) {
                    Music music = new Music("." + File.separator
                            + "Sounds" + File.separator
                            + "lightgun.mp3");
                    ThreadPool.execute(music);
                    return finalizeShoot(lightBulletImage, deg);
                } else {
                    Music music = new Music("." + File.separator
                            + "Sounds" + File.separator
                            + "emptyGun.mp3");
                    ThreadPool.execute(music);
                }
            }
        }
        return null;
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        Music music = new Music("." + File.separator
                + "Sounds" + File.separator
                + "enemybullettomytank.mp3");
        ThreadPool.execute(music);
        if (getHealth() < 0) {
            lives--;
            setHealth(initialHealth);
        }
        if (lives == 0) {
            setAlive(false);
            GameState.gameOver = true;
            System.exit(0);
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
        animation.changeCoordinates(locX, locY);
        if (isMoving) {
            ((Animation) animation).setActive(true);
        } else {
            ((Animation) animation).setActive(false);
        }
    }


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

    private void rot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        ((Animation) animation).setMovingRotationDeg(getAngle());
    }

    private void crossRot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        ((Animation) animation).setMovingRotationDeg(getAngle());
    }

    private double rotateTheCannon() {
        int dx = user.getMouseX() - (locX + 10);
        int dy = user.getMouseY() - (locY + 20);
        double deg = Math.atan2(dy, dx);
        ((Animation) animation).setCannonRotationDeg(deg);
        return deg;
    }

    public void update() {

        if (user.isKeyUP() || user.isKeyDOWN() || user.isKeyLEFT() || user.isKeyRIGHT()) {
            rotate();
            move();
        } else {
            ((Animation) animation).setActive(false);
        }
        double deg = 0;
//        if (user.isMouseMoved())
        deg = rotateTheCannon();
//        System.out.println(deg);
        if (user.isMouseLeftClickPressed()) {
            System.out.println(deg);
            Bullet bullet = shoot(deg);
            if (bullet != null) {
                ((Animation) animation).getBullets().add(bullet);
            }
//            synchronized(whichMap) {
//                whichMap.getAllObjects().add(shoot(deg));
//            }
        }
//        if (user.isMouseRightClickPressed())
//            changeWeapon();
//
    }

    @Override
    public void readContents(String location) {
        super.readContents(location);
    }

    public void recieveGift(Block gift) {
        if (gift.getType().equals("cf"))
            increaseCannonCount(20);

        whichMap.getAllObjects().remove(gift);
        whichMap.getVolatileObjects().remove(gift);

    }

    /**
     * changes the tank's weapon's animation.
     */
    public void changeTheGun() {
        if (isOnCannon) {
            ((Animation) animation).setGun(cannons[currentCannon]);
            bulletDamage = 20;
        } else {
            ((Animation) animation).setGun(rifles[currentRifle]);
            bulletDamage = 10;
        }
    }

    public boolean isOnCannon() {
        return isOnCannon;
    }

    public void setOnCannon(boolean onCannon) {
        isOnCannon = onCannon;
    }

    /**
     * changes the tank's weapon.
     */
    @Override
    public void changeWeapon() {
        setOnCannon(!isOnCannon);
        changeTheGun();
    }

//    @Override
//    public void displayTheAnimations() {
//        super.displayTheAnimations();
//    }

    public User getUser() {
        return user;
    }

    public int getLives() {
        return lives;
    }
}
