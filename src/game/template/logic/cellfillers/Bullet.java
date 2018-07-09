package game.template.logic.cellfillers;

import game.template.logic.Map;

public class Bullet extends GameObject {
    private char type;
    private int damage;

    public Bullet(int y, int x, Map whichMap, double shootingAngle, char type, String location) {
        super(y, x, true, 0, whichMap, location);
        this.type = type;
        if (type == 'c')
            damage = 200;
        else
            damage = 100;
        displayTheAnimations();
    }

    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * Shoots the bullet in the specified direction.
     */
    @Override
    public void update() {
        locX += velocity * Math.cos(getAngleInRadians());
        locY += velocity * Math.sin(getAngleInRadians());
        if (whichMap.doesntGoOutOfMap(this, true))
            setAlive(false);
    }


}
