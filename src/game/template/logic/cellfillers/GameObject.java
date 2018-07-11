package game.template.logic.cellfillers;

import game.template.graphics.Animation;
import game.template.graphics.MasterAnimation;
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
    protected game.template.graphics.MasterAnimation animation;
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
   // String location;


    public GameObject(int y, int x, boolean isDestructible, int health, Map whichMap, String location) {
        this(y, x, isDestructible, health, whichMap);
        //this.location = location;
        readContents(location);
    }

    public GameObject(int y, int x, boolean isDestructible, int health, Map whichMap) {
        this.isDestructible = isDestructible;
        this.health = health;
        this.whichMap = whichMap;
        //TODO: not actual values and need to be changed.
        locY = (y) * 120;
        locX = (x) * 120;
    }

    public GameObject() {
    }

    public void update() {
    }

    public void takeDamage(int damage) {
        if (isDestructible)
            health -= damage;
    }

    public int getHeight() {
        return animation.getFrameHeight();
    }

    public int getWidth() {
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

    public void readContents(String location) {
        if (Files.isDirectory(Paths.get(location))) {
            imageLocation = new File(location);
            imagesLocations = imageLocation.listFiles();
        } else
            imagesLocations = new File[]{new File(location)};
        images = new BufferedImage[imagesLocations.length];
        for (int i = 0; i < imagesLocations.length; i++) {
            try {
                images[i] = ImageIO.read(imagesLocations[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDamage() {
        return 0;
    }
    //    protected void changeDimension(int x, int y) {
//        int secX = this.x + x, secY = this.y + y;
//        if ((whichMap.getHeight() >= secY) && (0 <= secY))
//            this.y += y;
//        if ((whichMap.getWidth() >= secX) && (0 <= secX))
//            this.x += x;
//    }


    public double getAngleInRadians() {
        if (animation.active) {
            int tmp = angle % 360;
            if (tmp < 0)
                tmp = 360 + tmp;
            return Math.toRadians(tmp);
        }
        return Math.PI / 2;
    }

    public void displayTheAnimations() {
        animation = new MasterAnimation(images, 120, 120, 1, 20,
                false, locX, locY, 0);
        animation.active = false;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public Map getWhichMap() {
        return whichMap;
    }
}
