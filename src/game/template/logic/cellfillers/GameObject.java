package game.template.logic.cellfillers;

import game.template.graphics.Animation;
import game.template.graphics.MasterAnimation;
import game.template.logic.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Game, I am your father.
 * Basically is the blueprint for every visible thing in the game.
 */

public abstract class GameObject implements Serializable {

    // Its animation.
    protected transient game.template.graphics.MasterAnimation animation;
    private boolean isDestructible;
    private boolean isAlive;
    protected transient BufferedImage[] images;
    private int health;
    private File imageLocation;
    private File[] imagesLocations;
    protected int angle;
    //Its map
    protected Map whichMap;
    public int locX;
    public int locY;
    protected int velocity;
    private String location;
    // String location;

    /**
     * creates a new game object using the given parameters.
     *
     * @param y
     * @param y              its x-location
     * @param x              its y-location
     * @param isDestructible
     * @param health
     * @param whichMap       the map it is in
     * @param location       the location of its animation's content.
     */

    public GameObject(int y, int x, boolean isDestructible, int health, Map whichMap, String location) {
        this(y, x, isDestructible, health, whichMap);
        this.location = location;
        isAlive = true;
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

    /**
     * takes damage from other components.
     *
     * @param damage
     */

    public void takeDamage(int damage) {
        if (isDestructible)
            health -= damage;
    }

    /**
     * @return its animation's height.
     */

    public int getHeight() {
        return animation.getFrameHeight();
    }

    /**
     * @return its animation's width.
     */

    public int getWidth() {
        return animation.getFrameWidth();
    }

    /**
     * @return its animation
     */

    public MasterAnimation getAnimation() {
        return animation;
    }

    /**
     * @return whether or not it can be damaged.
     */

    public boolean isDestructible() {
        return isDestructible;
    }

    /**
     * @return its remaining health.
     */

    public int getHealth() {
        return health;
    }

    /**
     * set a new health for the object.
     *
     * @param health the new health.
     */

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return whether or not the object is alive.
     */

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * set a new alive situation for the object.
     *
     * @param alive the new sit.
     */

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public File[] getImagesLocations() {
        return imagesLocations;
    }

    /**
     * reads the content necessary for the object's animation.
     *
     * @param location where to read the info from.
     */

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

    /**
     * @return the amount of damage it can do to other objects.
     */

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

    /**
     * does the necessary calculations and
     *
     * @return its animation's rotating angle in radians.
     */

    public double getAngleInRadians() {
        if (animation.active) {
            int tmp = angle % 360;
            if (tmp < 0)
                tmp = 360 + tmp;
            return Math.toRadians(tmp);
        }
        return Math.PI / 2;
    }

    /**
     * creates its animation and puts it in the proper state.
     */

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

    /**
     * set a new destructibility state for the objecte
     *
     * @param destructible the new state
     */

    public void setDestructible(boolean destructible) {
        isDestructible = destructible;
    }

    /**
     * @return the location of its animation's content.
     */

    public String getLocation() {
        return location;
    }
}
