package game.template.logic.cellfillers;

import game.template.logic.Map;

public class ComputerTank extends Tank {
    private boolean isMobile;
    private char type;
    private UserTank enemyTank = null;
    private boolean temporarilyDisabled = true;
    private boolean doesCollisionDamageUserTank;
    private int damageInCaseCollisionIsDestructive = 2;

    public ComputerTank(int y, int x, int health, Map whichMap, boolean doesCollisionDamageUserTank, String location) {
        super(y, x, health, whichMap, location);
        if (whichMap.doesntGoOutOfMap(this, true))
            temporarilyDisabled = false;
        this.doesCollisionDamageUserTank = doesCollisionDamageUserTank;
    }

    /**
     * Remember to update only objects that ARE visible.
     */
    public void move() {
        if (isMobile) {
            findEnemyTank();
            int ySign = (locY - enemyTank.locY) / Math.abs(locY - enemyTank.locY);
            int plusY = velocity * ySign;
            locY += plusY;
            locY += avoidCollision() * ySign;
            int xSign = (locX - enemyTank.locX) / Math.abs(locX - enemyTank.locX);
            int plusX = velocity * xSign;
            locX += plusX + avoidCollision() * xSign;
        }
    }

    public void findEnemyTank() {
        if (enemyTank == null) {
            for (GameObject o : whichMap.getVisibleObjects()) {
                if (o instanceof UserTank)
                    enemyTank = (UserTank) o;
            }
        }
    }

    @Override
    public void shoot() {
        findEnemyTank();
        double angle = Math.atan((enemyTank.locY - locY) / (enemyTank.locX - locX));
        new Bullet(locY, locX, this.whichMap, angle, getCurrentWeaponType(), "Bullet Location");
    }


    @Override
    public void update() {
        if (isMobile && validateAbility())
            move();
        if (!temporarilyDisabled)
            shoot();
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (getHealth() < 0)
            setAlive(false);
    }

    private boolean validateAbility() {
        if (whichMap.doesntGoOutOfMap(this, true))
            temporarilyDisabled = false;
        else
            temporarilyDisabled = true;
        return !temporarilyDisabled;
    }

    public boolean isDoesCollisionDamageUserTank() {
        return doesCollisionDamageUserTank;
    }

    @Override
    public int getDamage() {
        return damageInCaseCollisionIsDestructive;
    }

}


//    int ySign = (int)(state.locY - enemyTank.state.locY) / Math.abs(state.locY - enemyTank.state.locY);
//            int plusY = 8 * ySign;
//            boolean isYDone = false;
//            while (!isYDone)
//            {
//                state.locY += plusY;
//                isYDone = true;
//                for (GameObject o: whichMap.getVisibleObjects())
//                {
//                    if (GameState.checkIfTwoObjectsCollide(this, o))
//                    {
//                        state.locY -= plusY;
//                        plusY -=2;
//                        isYDone = false;
//                        break;
//                    }
//                }
//                if (plusY == 0)
//                    isYDone = true;
//            }