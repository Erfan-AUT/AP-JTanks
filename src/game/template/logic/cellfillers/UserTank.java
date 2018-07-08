package game.template.logic.cellfillers;

import game.sample.ball.GameLoop;
import game.sample.ball.GameState;
import game.template.graphics.Animation;
import game.template.logic.Map;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTank extends Tank {

    private int lives = 3;
    private int initialHealth;
    private int rotationDegree;
    private game.template.bufferstrategy.GameState gameState;
    protected boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;

    public UserTank(int y, int x, int health, Map whichMap, String location, game.template.bufferstrategy.GameState gameState) {
        super(y, x, health, whichMap, location);
        initialHealth = health;
        this.gameState = gameState;
        setVelocity(10);
        readContents();
        animation = new Animation(images, 250, 250, 4, 20, false, x, y, 0);
    }

    private class MouseObserver extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e))
                changeWeapon();
            if (SwingUtilities.isLeftMouseButton(e))
                shoot();
        }
    }

    @Override
    public void shoot() {
        boolean check;
        if (getCurrentWeaponType() == 'c')
            check = decreaseCannonCount();
        else
            check = decreaseRifleCount();
        if (check)
            //This should be changed to cannon's location.
            new Bullet(locY, locX,
                    this.whichMap, getAngleInRadians(), getCurrentWeaponType(), "BulletLocation");
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (getHealth() < 0) {
            lives--;
            setHealth(initialHealth);
        }
        if (lives == 0) {
            setAlive(false);
            GameState.gameOver = true;
        }
    }

    @Override
    public void move() {
        boolean isMoving = false;
        int x = locX;
        int y = locY;
        if (gameState.isKeyUP()) {
            y -= velocity + avoidCollision();
            isMoving = true;
        } else if (gameState.isKeyDOWN()) {
            y += velocity + avoidCollision();
            isMoving = true;
        }
        if (gameState.isKeyRIGHT()) {
            x += velocity + avoidCollision();
            isMoving = true;
        } else if (gameState.isKeyLEFT()) {
            x -= velocity + avoidCollision();
            isMoving = true;
        }
        locX = x;
        locY = y;
//        setCannonX(getX() + 75);
//        setCannonY(getY() + 75);
        animation.changeCoordinates(locX, locY);
        if (isMoving) {
            animation.setActive(true);
        } else {
            animation.setActive(false);
        }
    }


    public game.template.bufferstrategy.GameState getGameState() {
        return gameState;
    }

    public void rotate() {
        int tmp = getAngle() % 360;
        int x = locX;
        int y = locY;
        boolean isMoving = false;
        if (tmp < 0) {
            setAngle(360 + tmp);
        } else {
            setAngle(tmp);
        }

        if (gameState.isKeyUP() && gameState.isKeyRIGHT() || gameState.isKeyRIGHT() && gameState.isKeyUP()) {
            if (getAngle() >= 180 && getAngle() < 305) {
                crossRot(5);
            } else if (getAngle() == 305) {
                setVelocity(10);
            } else {
                crossRot(-5);
            }
        } else if (gameState.isKeyUP() && gameState.isKeyLEFT() || gameState.isKeyLEFT() && gameState.isKeyUP()) {
            if ((getAngle() <= 360 && getAngle() > 215) || getAngle() == 0) {
                crossRot(-5);
            } else if (getAngle() == 215) {
                setVelocity(10);
            } else {
                crossRot(5);
            }
        } else if (gameState.isKeyDOWN() && gameState.isKeyLEFT()) {
            if (getAngle() >= 0 && getAngle() < 135) {
                crossRot(5);
            } else if (getAngle() == 135) {
                setVelocity(10);
            } else {
                crossRot(-5);
            }
        } else if (gameState.isKeyDOWN() && gameState.isKeyRIGHT()) {
            if (getAngle() <= 180 && getAngle() > 45) {
                crossRot(-5);
            } else if (getAngle() == 45) {
                setVelocity(10);
            } else {
                crossRot(5);
            }
        }
        if (gameState.isKeyRIGHT() && !(gameState.isKeyUP() || gameState.isKeyDOWN())) {
            if (getAngle() != 0 && getAngle() != 180) {
                if (getAngle() < 180) {
                    rot(-5);
                    System.out.println(getAngle());
                } else {
                    rot(5);
                    System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 0) {
                    setForward(true);
                    setVelocity(10);
                    System.out.println(getAngle());
                } else {
                    setForward(false);
                    setVelocity(10);
                    System.out.println(getAngle());
                }
            }
        } else if (gameState.isKeyLEFT() && !(gameState.isKeyUP() || gameState.isKeyDOWN())) {
            if (getAngle() != 0 && getAngle() != 180) {
                if (getAngle() < 180) {
                    rot(5);
                    System.out.println(getAngle());
                } else {
                    rot(-5);
                    System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 0) {
                    setForward(false);
                    setVelocity(10);
                    System.out.println(getAngle());
                } else {
                    setForward(true);
                    setVelocity(10);
                    System.out.println(getAngle());
                }
            }
        } else if (gameState.isKeyUP() && !(gameState.isKeyRIGHT() || gameState.isKeyLEFT())) {
            if (getAngle() != 90 && getAngle() != 270) {
                if (getAngle() > 90 && getAngle() < 270) {
                    rot(5);
                    System.out.println(getAngle());
                } else {
                    rot(-5);
                    System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 270) {
                    setForward(true);
                    setVelocity(10);
                    System.out.println(getAngle());
                } else {
                    setForward(false);
                    setVelocity(10);
                    System.out.println(getAngle());
                }
            }
        } else if (gameState.isKeyDOWN() && !(gameState.isKeyRIGHT() || gameState.isKeyLEFT())) {
            if (getAngle() != 90 && getAngle() != 270) {
                if (getAngle() > 90 && getAngle() < 270) {
                    rot(-5);
                    System.out.println(getAngle());
                } else {
                    rot(5);
                    System.out.println(getAngle());
                }
            } else {
                if (getAngle() == 90) {
                    setForward(true);
                    setVelocity(10);
                    System.out.println(getAngle());
                } else {
                    setForward(false);
                    setVelocity(10);
                    System.out.println(getAngle());
                }
            }
        }
    }

    private void rot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        animation.setMovingRotationDeg(getAngle());
    }

    private void crossRot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        animation.setMovingRotationDeg(getAngle());
    }

    private void rotateTheCannon() {
        int dx = gameState.getMouseX() - (locX + 10);
        int dy = gameState.getMouseY() - (locY + 20);
        double deg = Math.atan2(dy, dx);
        animation.setCannonRotationDeg(deg);
    }

    public void update() {
        rotate();
        move();
        rotateTheCannon();
    }

}
