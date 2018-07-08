package game.template.Tanks;

import game.template.Graphics.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Tank {
    protected BufferedImage[] tankImages;
    protected BufferedImage bullet;
    protected Animation tankMove;
    protected Animation explosion;
    private File imageLocation;
    private File[] imagesLocations;
    private int health;
    private int damage;
    private int x;
    private int y;
    private int cannonX;
    private int cannonY;
    private int angle;
    private int velocity;
    private boolean forward;

    public Tank(String location) {
        imageLocation = new File(location);
        imagesLocations = imageLocation.listFiles();
        tankImages = new BufferedImage[imagesLocations.length];
        forward = false;
        angle = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public abstract void move();

    protected void readContents() {
        for (int i = 0; i < imagesLocations.length; i++) {
            try {
                tankImages[i] = ImageIO.read(imagesLocations[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Animation getTankMove() {
        return tankMove;
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

    public abstract void rotate();

    public BufferedImage[] getTankImages() {
        return tankImages;
    }

    public int getCannonX() {
        return cannonX;
    }

    public void setCannonX(int cannonX) {
        this.cannonX = cannonX;
    }

    public int getCannonY() {
        return cannonY;
    }

    public void setCannonY(int cannonY) {
        this.cannonY = cannonY;
    }

    public abstract void update();
}