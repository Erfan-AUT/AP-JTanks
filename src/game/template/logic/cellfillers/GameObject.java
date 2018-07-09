package game.template.logic.cellfillers;

import game.template.Graphics.MasterAnimation;
import game.template.graphics.Animation;
import game.template.logic.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Game, I am your father.
 * Basically is the blueprint for every visible thing in the game.
 */

public abstract class GameObject {

    // Its animation.
    protected game.template.Graphics.MasterAnimation animation;
    private boolean isDestructible;
    private boolean isAlive;
    protected BufferedImage[] images;
    private int health;
    private File imageLocation;
    private File[] imagesLocations;
    protected int angle;
    //Its map
    protected Map whichMap;
    public int locX;
    public int locY;
    protected int velocity;


    public GameObject(int y, int x, boolean isDestructible, int health, Map whichMap, String location) {
        this.isDestructible = isDestructible;
        this.health = health;
        this.whichMap = whichMap;
        if (Files.isDirectory(Paths.get(location))) {
            imageLocation = new File(location);
            imagesLocations = imageLocation.listFiles();
        }
        else
            imagesLocations = new File[] {new File(location)};
        images = new BufferedImage[imagesLocations.length];
        //TODO: not actual values and need to be changed.
        locY = y;
        locX = x;
        readContents();
    }

    public void update(){}

    public void takeDamage(int damage) {
        if (isDestructible)
            health -= damage;
    }

    public int getHeight()
    {
        return animation.getFrameHeight();
    }

    public int getWidth()
    {
        return animation.getFrameWidth();
    }

    public MasterAnimation getAnimation() {
        return animation;
    }

    public boolean isDestructible() {
        return isDestructible;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public File[] getImagesLocations() {
        return imagesLocations;
    }

    protected void readContents() {
        for (int i = 0; i < imagesLocations.length; i++) {
            try {
                images[i] = ImageIO.read(imagesLocations[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDamage(){return  0;}
    //    protected void changeDimension(int x, int y) {
//        int secX = this.x + x, secY = this.y + y;
//        if ((whichMap.getHeight() >= secY) && (0 <= secY))
//            this.y += y;
//        if ((whichMap.getWidth() >= secX) && (0 <= secX))
//            this.x += x;
//    }


    public double getAngleInRadians() {
        int tmp = angle % 360;
        if (tmp < 0)
            tmp = 360 + tmp;
        return Math.toRadians(tmp);
    }

    public void displayTheAnimations()
    {
        animation = new MasterAnimation(images, 250, 250, 1, 20,
                false, locX, locY, 0);
        animation.active = true;
    }


    public BufferedImage[] getImages() {
        return images;
    }

    public Map getWhichMap() {
        return whichMap;
    }
}
