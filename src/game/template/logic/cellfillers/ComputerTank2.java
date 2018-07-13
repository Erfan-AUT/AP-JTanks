package game.template.logic.cellfillers;

import game.template.graphics.Animation;
import game.template.graphics.MasterAnimation;
import game.template.logic.Map;

public class ComputerTank2 extends ComputerTank {

    private int deg = 180;

    public ComputerTank2(int y, int x, int health, Map whichMap, boolean doesCollisionDamageUserTank, String location, boolean isMobile) {
        super(y, x, health, whichMap, doesCollisionDamageUserTank, location);
        this.isMobile = isMobile;
        animation = new Animation(images, 120, 120, 8, 20,
                false, locX, locY, 0);
        setVelocity(10);
        setAlive(true);
    }

    public void moveIt() {
        animation.active = true;
        if (Math.abs(locY - enemyTank.locY) < 120 && Math.abs(locX - enemyTank.locX) < 120) {
            setAlive(false);
            Map.addANewExp(locX,locY);
        }
        deg %= 360;
        if (deg < 0) {
            deg = 360 + deg;
        }
        if (Math.abs(locX - enemyTank.locX) > 75) {
            int xSign = -(locX - enemyTank.locX) / Math.abs(locX - enemyTank.locX);
            int plusX = getVelocity() * xSign;
            System.out.println(avoidCollision());
            locX += plusX + xSign * avoidCollision();
            animation.changeCoordinates(locX, locY);
            if (deg != 0 && deg != 180) {
                if (locX > enemyTank.locX) {
                    deg += 5;
                    ((Animation) animation).setMovingRotationDeg(deg);
                } else {
                    deg -= 5;
                    ((Animation) animation).setMovingRotationDeg(deg);
                }
                setForward(false);
            } else {
                if (locX > enemyTank.locX) {
                    setForward(false);
                } else {
                    setForward(true);
                }
            }
        } else if (Math.abs(locY - enemyTank.locY) > 0) {
            int ySign = -(locY - enemyTank.locY) / Math.abs(locY - enemyTank.locY);
            int plusY = getVelocity() * ySign;
            locY += plusY + ySign * avoidCollision() ;
            animation.changeCoordinates(locX, locY);
            if (deg != 90 && deg != 270) {
                if (locY > enemyTank.locY) {
                    deg += 5;
                    ((Animation) animation).setMovingRotationDeg(deg);
                } else {
                    deg -= 5;
                    ((Animation) animation).setMovingRotationDeg(deg);
                }
                setForward(false);
            } else {
                if (locY > enemyTank.locY) {
                    setForward(false);
                } else {
                    setForward(true);
                }
            }
        }

    }

    @Override
    public void findEnemyTank() {
        super.findEnemyTank();
    }

    @Override
    public void update() {
        animation.active = false;
        findEnemyTank();
        if (isMobile && validateAbility() && isAlive())
            moveIt();
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
    }

    @Override
    public boolean isDoesCollisionDamageUserTank() {
        return super.isDoesCollisionDamageUserTank();
    }

    @Override
    public int getDamage() {
        return super.getDamage();
    }

    @Override
    public MasterAnimation getAnimation() {
        return super.getAnimation();
    }

    @Override
    protected boolean validateAbility() {
        return super.validateAbility();
    }
}
