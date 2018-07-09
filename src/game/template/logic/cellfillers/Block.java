package game.template.logic.cellfillers;

import game.template.Graphics.MasterAnimation;
import game.template.logic.Map;

/**
 * basis of every block that the user sees in the game.
 */
public class Block extends GameObject {

    //Whether or not tanks or bullets can pass through it.
    private boolean isPassableByTank = false;
    private boolean isPassableByBullet = false;
    private boolean isGift;

    public Block(int y, int x, boolean isDestructible, int health, Map whichMap, int pass, String location, boolean isGift) {
        super(y, x, isDestructible, health, whichMap, location);
        if (isGift) {
            pass = 0;
            isDestructible = true;
        }
        if (pass == 0)
            isDestructible = false;
        //TODO : Note that in collision of gift block and user tank, gift gets added to tank.
        //Jeez.
        switch (pass)
        {
            case 0:
                isPassableByTank = true;
            case 1:
                isPassableByBullet = true;
                break;
        }
       displayTheAnimations();
        //Any other means non-passable.
    }

    public boolean isPassableByTank() {
        return isPassableByTank;
    }

    public boolean isPassableByBullet() {
        return isPassableByBullet;
    }
}
