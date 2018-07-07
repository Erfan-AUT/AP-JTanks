package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.ObjectState;

/**
 * basis of every block that the user sees in the game.
 */
public class Block extends GameObject {

    private ObjectState state;
    //Whether or not tanks or bullets can pass through it.
    private boolean isPassableByTank = false;
    private boolean isPassableByBullet = false;

    public Block(int y, int x, boolean isDestructible, int health, Map whichMap, int pass) {
        super(isDestructible, health, whichMap);
        state = new ObjectState(y, x, whichMap);
        if (pass == 1)
            isPassableByBullet = true;
        if (pass == 0)
            isPassableByTank = true;
        //Any other means non-passable.

    }

    public boolean isPassableByTank() {
        return isPassableByTank;
    }

    public boolean isPassableByBullet() {
        return isPassableByBullet;
    }
}
