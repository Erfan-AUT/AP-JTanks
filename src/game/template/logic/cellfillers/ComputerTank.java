package game.template.logic;

import java.awt.*;

public class ComputerTank extends Tank {
    private boolean isMobile;
    private char type;

    public ComputerTank(int y, int x, boolean isDestructible, int health, Map whichMap) {
        super(isDestructible, health, whichMap);
        tankState = new ComputerTankState(y, x);

    }

    public void move()
    {

    }

    public void whereToMoveNext()
    {

    }
}
