package game.template;

import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet implements Runnable {
    private BufferedImage bullet;
    private int x;
    private int y;
    private int velocity;
    private double coeX;
    private double coeY;
    private double deg;
    boolean isActive;

    public Bullet(BufferedImage bullet, int x, int y, double coeX, double coeY, double deg) {
        isActive = true;
        this.bullet = bullet;
        this.x = x;
        this.y = y;
        this.deg = deg;
        this.velocity = 15;
        this.coeX = coeX;
        this.coeY = coeY;
    }

    public void move() {
        x += coeX * velocity;
        y += coeY * velocity;
    }

    @Override
    public void run() {
        while (isActive) {
            move();
            try {
                Thread.sleep(24);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x > 1280 || y > 720) {
                isActive = false;
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getBullet() {
        return bullet;
    }

    public double getDeg() {
        return deg;
    }

}
