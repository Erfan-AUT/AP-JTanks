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
        super(y, x, health, whichMap, location, ".\\Images\\EnemyBullet1.png");
        this.doesCollisionDamageUserTank = doesCollisionDamageUserTank;
        displayTheAnimations();
        if (whichMap.doesntGoOutOfMap(this, false, 0))
            temporarilyDisabled = false;
    }

     //Remember to update only objects that ARE visible.

    /**
     * moves the tank using its internal parameters.
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

    /**
     * finds the user's tank.
     */

    public void findEnemyTank() {
        if (enemyTank == null) {
            for (GameObject o : whichMap.getVisibleObjects()) {
                if (o instanceof UserTank)
                    enemyTank = (UserTank) o;
            }
        }
    }

    /**
     * shoots a new bullet at a certain degree of rotation
     * @param deg the degree
     * @return the bullet.
     */
    @Override
    public Bullet shoot(double deg) {
        findEnemyTank();

        return new Bullet(heavyBulletImage, (int) (locX + 67 + Math.cos(deg) * 100),
                (int) (locY + 75 + Math.sin(deg) * (100)), Math.cos(deg), Math.sin(deg), deg, whichMap, 5);
    }


    /**
     * updates the tank given its new state.
     */
    @Override
    public void update() {
        if (isMobile && validateAbility())
            move();
        if (!temporarilyDisabled);
        {
            double deg = Math.atan((enemyTank.locY - locY) / (enemyTank.locX - locX));
            shoot(deg);
        }
            //shoot();
    }
    /**
     * takes damage from other components.
     *
     * @param damage
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (getHealth() < 0)
            setAlive(false);
    }

    /**
     * validates whether or not it can attack the user's tank
     * @return true for yes, false for no.
     */
    private boolean validateAbility() {
        if (whichMap.doesntGoOutOfMap(this, false, 0))
            temporarilyDisabled = false;
        else
            temporarilyDisabled = true;
        return !temporarilyDisabled;
    }

    /**
     * whether or not its collision with the user tank create some damage.
     * @return yes or no.
     */

    public boolean isDoesCollisionDamageUserTank() {
        return doesCollisionDamageUserTank;
    }

    @Override
    public int getDamage() {
        return damageInCaseCollisionIsDestructive;
    }

    //TODO: load transient fields for the pc tank.
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