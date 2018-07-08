package game.template.logic.cellfillers;

import game.template.logic.Map;

/**
 * basis of every block that the user sees in the game.
 */
public class Block extends GameObject {

    private ObjectState state;
    //Whether or not tanks or bullets can pass through it.
    private boolean isPassableByTank = false;
    private boolean isPassableByBullet = false;

    public Block(int y, int x, boolean isDestructible, int health, Map whichMap, int pass, String location) {
        super(y, x, isDestructible, health, whichMap, location);
        switch (pass)
        {
            case 0:
                isPassableByTank = true;
            case 1:
                isPassableByBullet = true;
                break;
        }
        //Any other means non-passable.
        state = new ObjectState(y, x, whichMap);
    }

    public boolean isPassableByTank() {
        return isPassableByTank;
    }

    public boolean isPassableByBullet() {
        return isPassableByBullet;
    }
}
