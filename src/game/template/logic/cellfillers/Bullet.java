package game.template.logic.cellfillers;

import game.template.logic.Map;
import game.template.logic.objectstates.BulletState;

public class Bullet extends GameObject {
    private char type;
    private int damage;

    public Bullet(int y, int x, int health, Map whichMap, double shootingAngle, char type) {
        super(true, health, whichMap);
        state = new BulletState(y, x, shootingAngle);
        this.type = type;
        if (type == 'c')
            damage = 200;
        else
            damage = 100;
    }

    public int getDamage() {
        return damage;
    }
}
