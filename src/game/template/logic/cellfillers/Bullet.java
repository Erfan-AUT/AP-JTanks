package game.template.logic.cellfillers;

import game.template.logic.Map;

import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
    private char type;
    private int damage;
    private BufferedImage bullet;
    private int velocity;
    private double coeX;
    private double coeY;
    private double deg;
    private boolean isActive;

    public Bullet(BufferedImage bullet, int x, int y, double coeX, double coeY, double deg, Map whichMap) {
        super(y, x, true, 0, whichMap);
        isActive = true;
        this.bullet = bullet;
        this.deg = deg;
        this.velocity = 2;
        this.coeX = coeX;
        this.coeY = coeY;
        displayTheAnimations();
    }
//
//    public void move() {
//        x += coeX * velocity;
//        y += coeY * velocity;
//    }

//    @Override
//    public void run() {
//        while (isActive) {
//            update();
//            try {
//                Thread.sleep(24);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

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

    /**
     * Shoots the bullet in the specified direction.
     */
    @Override
    public void update() {
        locX += velocity * Math.cos(getAngleInRadians());
        locY += velocity * Math.sin(getAngleInRadians());
        if (!whichMap.doesntGoOutOfMap(this, true))
            setAlive(false);
    }


}
