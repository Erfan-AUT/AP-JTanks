package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.ObjectState;

/**
 * basis of every block that the user sees in the game.
 */
public class Block extends GameObject {

    private ObjectState state;
    //Whether or not tanks can pass through it.
    private boolean isPassable;
    public Block(int y, int x, boolean isDestructible, int health, Map whichMap) {
        super(isDestructible, health, whichMap);
        state = new ObjectState(y, x);
    }
}
