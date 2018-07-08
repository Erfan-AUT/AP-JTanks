package game.template.logic;

import game.template.graphics.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Tan2k {

    protected Animation animation;


    private int health;
    private int damage;
    private int x;
    private int y;
    private int cannonX;
    private int cannonY;


    public Tan2k(String location) {

    }


    public abstract void move();



    public Animation getAnimation() {
        return animation;
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