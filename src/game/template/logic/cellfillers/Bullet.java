package game.template.logic.cellfillers;

import game.template.graphics.MasterAnimation;
import game.template.logic.Map;

import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Runnable {
    private char type;
    private int damage;
    private transient BufferedImage bullet;
    private int velocity;
    private double coeX;
    private double coeY;
    private double deg;
    private boolean isActive;

    public Bullet(BufferedImage bullet, int x, int y, double coeX, double coeY, double deg, Map whichMap, int damage) {
        //super(y, x, true, 0, whichMap);
        locY = y;
        locX = x;
//        locX = (int) (locX + 67 + Math.cos(deg) * 100);
//        locY = (int) (locY + 75 + Math.sin(deg) * (100));
        isActive = true;
        this.bullet = bullet;
        this.deg = deg;
        this.velocity = 20;
        this.coeX = coeX;
        this.coeY = coeY;
        this.whichMap = whichMap;
        setAlive(true);
        displayTheAnimations();
        this.damage = damage;
    }
//
//    public void move() {
//        x += coeX * velocity;
//        y += coeY * velocity;
//    }

    @Override
    public void run() {
        while (isActive) {
            if (!isAlive())
                Thread.currentThread().stop();
            update();

            try {
                Thread.sleep(24);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public Bullet(int y, int x, Map whichMap, double shootingAngle, char type, String location) {
//        super(y, x, true, 0, whichMap, location);
//        this.type = type;
//        if (type == 'c')
//            damage = 200;
//        else
//            damage = 100;
//        displayTheAnimations();
//    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void displayTheAnimations() {
        animation = new MasterAnimation(images, 20, 20, 1, 20,
                false, locX, locY, 0);
        animation.active = true;
    }

    /**
     * Shoots the bullet in the specified direction.
     */
    @Override
    public void update() {
        locX += velocity * coeX;
        locY += velocity * coeY;
        if (!whichMap.doesntGoOutOfMap(this, false, 0))
            setAlive(false);
    }

    public double getDeg() {
        return deg;
    }

    public BufferedImage getBullet() {
        return bullet;
    }

//    public void stopThread()
//    {
//        Thread.currentThread().stop();
//    }

}
