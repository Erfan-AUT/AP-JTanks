package game.template.logic;

import java.awt.*;

public class Block extends GameObject {

    private ObjectState state;
    public Block(int y, int x, boolean isDestructible, int health, Map whichMap) {
        super(isDestructible, health, whichMap);
        state = new ObjectState(y, x);
    }
}
