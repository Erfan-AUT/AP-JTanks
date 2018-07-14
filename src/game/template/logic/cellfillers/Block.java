package game.template.logic.cellfillers;

import game.template.bufferstrategy.GameFrame;
import game.template.bufferstrategy.ThreadPool;
import game.template.graphics.MasterAnimation;
import game.template.logic.Map;
import game.template.logic.utils.Music;

/**
 * basis of every block that the user sees in the game.
 */
public class Block extends GameObject {

    //Whether or not tanks or bullets can pass through it.
    private boolean isPassableByTank = false;
    private boolean isPassableByBullet = false;
    private int initialHealth;
    private boolean isGift;
    private String type;

    /**
     * creates a new block using the properly named parameters..
     * @param y its x-location
     * @param x its y-location
     * @param isDestructible
     * @param health
     * @param whichMap the map it is in
     * @param pass which objects can pass through it.
     * @param location the location of its animation's content.
     * @param isGift whether or not it is a gift.
     * @param type the type of it.
     */
    public Block(int y, int x, boolean isDestructible, int health, Map whichMap, int pass,
                 String location, boolean isGift, String type) {
        super(y, x, isDestructible, health, whichMap, location);
        this.type = type;
        initialHealth = health;
        this.isGift = isGift;
        if (pass == 0)
            setDestructible(false);
        if (isGift) {
            pass = 0;
            setDestructible(true);
        }
        //TODO : Note that in collision of gift block and user tank, gift gets added to tank.
        //Jeez.
        switch (pass) {
            case 0:
                isPassableByTank = true;
            case 1:
                isPassableByBullet = true;
                break;
        }
        displayTheAnimations();
        //Any other means non-passable.
    }
    /**
     * takes damage from other components.
     * @param damage
     */
    @Override
    public void takeDamage(int damage) {
        if (isDestructible()) {
            super.takeDamage(1);
            //location = ;
            if (getHealth() > 0)
            {
                readContents(".\\images\\softWall" + Integer.toString(4 - getHealth()) + ".png");
                Music music = new Music(".\\Sounds\\softwall.mp3");
                ThreadPool.execute(music);
                displayTheAnimations();
            }
            if (getHealth() <= 0) {
                whichMap.getAllObjects().remove(this);
                whichMap.getVolatileObjects().remove(this);
                setAlive(false);
            }
        }
    }


    /**
     * @return whether or not it is passable by a tank.
     */

    public boolean isPassableByTank() {
        return isPassableByTank;
    }
    /**
     * @return whether or not it's passable by a bullet.
     */

    public boolean isPassableByBullet() {
        return isPassableByBullet;
    }

    /**
     * @return whether or not it is a gift.
     */

    public boolean isGift() {
        return isGift;
    }


    /**
     * @return the type of the block.
     */

    public String getType() {
        return type;
    }
}
