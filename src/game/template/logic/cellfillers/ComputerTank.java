package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.ComputerTankState;

public class ComputerTank extends Tank {
    private boolean isMobile;
    private char type;

    public ComputerTank(int y, int x, int health, Map whichMap) {
        super(health, whichMap);
        state = new ComputerTankState(y, x);
    }

    public void move()
    {

    }

    public void whereToMoveNext()
    {

    }
}
