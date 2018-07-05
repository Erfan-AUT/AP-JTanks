package game.template.logic.cellfillers;

import game.sample.ball.GameState;
import game.template.logic.Map;
import game.template.logic.objectstates.ComputerTankState;
import game.template.logic.objectstates.TankState;
import game.template.logic.objectstates.UserTankState;

public class ComputerTank extends Tank {
    private boolean isMobile;
    private char type;
    private UserTank enemyTank = null;

    public ComputerTank(int y, int x, int health, Map whichMap) {
        super(health, whichMap);
        state = new ComputerTankState(y, x, this);
    }

    /**
     * Remember to update only objects that ARE visible.
     */
    public void move() {
        findEnemyTank();
        int ySign = (state.locY - enemyTank.state.locY) / Math.abs(state.locY - enemyTank.state.locY);
        int plusY = 8 * ySign;
        state.locY += plusY;
        state.locY += ((TankState) state).avoidCollision() * ySign;
        int xSign = (state.locX - enemyTank.state.locX) / Math.abs(state.locX - enemyTank.state.locX);
        int plusX = 8 * xSign;
        state.locX += plusX;
        state.locX += ((TankState) state).avoidCollision();
    }

    public void findEnemyTank() {
        if (enemyTank == null) {
            for (GameObject o : whichMap.getVisibleObjects()) {
                if (o instanceof UserTank)
                    enemyTank = (UserTank) o;
            }
        }
    }

    public void shoot() {
        findEnemyTank();
        double angle = Math.atan((enemyTank.state.locY - state.locY) / (enemyTank.state.locX - state.locX));
        new Bullet(state.locY, state.locX, this.whichMap, angle, getCurrentWeaponType());
    }


    @Override
    public void update() {
        if (isMobile)
            move();
        shoot();
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