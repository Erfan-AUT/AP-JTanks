package game.template.logic;

import game.template.graphics.Animation;

import java.awt.*;

public abstract class GameObject {

    protected Animation animation;
    private boolean isDestructible;
    private int health;
    private Map whichMap;

    public GameObject(boolean isDestructible, int health, Map whichMap) {
        this.isDestructible = isDestructible;
        this.health = health;
        this.whichMap = whichMap;
    }

    public void takeDamage(int damage) {
        if (isDestructible)
            health -= damage;
    }

//    protected void changeDimension(int x, int y) {
//        int secX = this.x + x, secY = this.y + y;
//        if ((whichMap.getHeight() >= secY) && (0 <= secY))
//            this.y += y;
//        if ((whichMap.getWidth() >= secX) && (0 <= secX))
//            this.x += x;
//    }



}
