package game.template.logic.cellfillers;

import game.template.graphics.Animation;
import game.template.logic.Map;
import game.template.logic.objectstates.ObjectState;

import java.awt.*;

/**
 * Game, I am your father.
 * Basically is the blueprint for every visible thing in the game.
 */

public abstract class GameObject {

    // Its animation.
    protected Animation animation;
    private boolean isDestructible;
    private boolean isAlive;
    private int health;

    //Its map
    protected Map whichMap;

    //Its state, taken directly from the template's model for updating animate objects.
    protected ObjectState state;

    public GameObject(boolean isDestructible, int health, Map whichMap) {
        this.isDestructible = isDestructible;
        this.health = health;
        this.whichMap = whichMap;
    }

    public void update(){}

    public void takeDamage(int damage) {
        if (isDestructible)
            health -= damage;
    }

    public ObjectState getState() {
        return state;
    }

    public int getHeight()
    {
        return animation.getFrameHeight();
    }

    public int getWidth()
    {
        return animation.getFrameWidth();
    }

    public Animation getAnimation() {
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

    public int getDamage(){return  0;}
    //    protected void changeDimension(int x, int y) {
//        int secX = this.x + x, secY = this.y + y;
//        if ((whichMap.getHeight() >= secY) && (0 <= secY))
//            this.y += y;
//        if ((whichMap.getWidth() >= secX) && (0 <= secX))
//            this.x += x;
//    }


    public Map getWhichMap() {
        return whichMap;
    }
}
