package game.template.logic.cellfillers;

import game.template.bufferstrategy.GameState;
import game.template.graphics.Animation;
import game.template.logic.Map;
import game.template.logic.NetworkUser;
import game.template.logic.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTank extends Tank {

    private int lives = 3;
    private int initialHealth;
    private int rotationDegree;
    private User user;

    public UserTank(int y, int x, int health, Map whichMap, String location) {
        super(y, x, health, whichMap, location);
        initialHealth = health;
        setVelocity(10);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void shoot() {
        boolean check;
        if (getCurrentWeaponType() == 'c')
            check = decreaseCannonCount();
        else
            check = decreaseRifleCount();
        if (check) {
            //This should be changed to cannon's location.
            String location;
            if (getCurrentWeaponType() == 'r')
                location = "\\images\\LightBullet.png";
            else
                location = "\\images\\HeavyBullet.png";
            new Bullet(locY, locX,
                    this.whichMap, getAngleInRadians(), getCurrentWeaponType(), location);
        }
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
        if (user.isKeyUP()) {
            locY -= velocity;
            locY -= avoidCollision();
            isMoving = true;
        } else if (user.isKeyDOWN()) {
            locY += velocity;
            locY += avoidCollision();
            isMoving = true;
        }
        if (user.isKeyRIGHT()) {
            locX += velocity;
            locX += avoidCollision();
            isMoving = true;
        } else if (user.isKeyLEFT()) {
            locX -= velocity;
            locX -= avoidCollision();
            isMoving = true;
        }
//        setCannonX(getX() + 75);
//        setCannonY(getY() + 75);
        animation.changeCoordinates(locX, locY);
        if (isMoving) {
            ((Animation) animation).setActive(true);
        } else {
            ((Animation) animation).setActive(false);
        }
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

        if (user.isKeyUP() && user.isKeyRIGHT() || user.isKeyRIGHT() && user.isKeyUP()) {
            if (getAngle() >= 180 && getAngle() < 305) {
                crossRot(5);
            } else if (getAngle() == 305) {
                setVelocity(10);
            } else {
                crossRot(-5);
            }
        } else if (user.isKeyUP() && user.isKeyLEFT() || user.isKeyLEFT() && user.isKeyUP()) {
            if ((getAngle() <= 360 && getAngle() > 215) || getAngle() == 0) {
                crossRot(-5);
            } else if (getAngle() == 215) {
                setVelocity(10);
            } else {
                crossRot(5);
            }
        } else if (user.isKeyDOWN() && user.isKeyLEFT()) {
            if (getAngle() >= 0 && getAngle() < 135) {
                crossRot(5);
            } else if (getAngle() == 135) {
                setVelocity(10);
            } else {
                crossRot(-5);
            }
        } else if (user.isKeyDOWN() && user.isKeyRIGHT()) {
            if (getAngle() <= 180 && getAngle() > 45) {
                crossRot(-5);
            } else if (getAngle() == 45) {
                setVelocity(10);
            } else {
                crossRot(5);
            }
        }
        if (user.isKeyRIGHT() && !(user.isKeyUP() || user.isKeyDOWN())) {
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
        } else if (user.isKeyLEFT() && !(user.isKeyUP() || user.isKeyDOWN())) {
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
        } else if (user.isKeyUP() && !(user.isKeyRIGHT() || user.isKeyLEFT())) {
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
        } else if (user.isKeyDOWN() && !(user.isKeyRIGHT() || user.isKeyLEFT())) {
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
        ((Animation) animation).setMovingRotationDeg(getAngle());
    }

    private void crossRot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        ((Animation) animation).setMovingRotationDeg(getAngle());
    }

    private void rotateTheCannon() {
        int dx = user.getMouseX() - (locX + 10);
        int dy = user.getMouseY() - (locY + 20);
        double deg = Math.atan2(dy, dx);
        ((Animation) animation).setCannonRotationDeg(deg);
    }

    public void update() {
        if (user.isKeyUP() || user.isKeyDOWN() || user.isKeyLEFT() || user.isKeyRIGHT()) {
            rotate();
            move();
        }
        if (user.isMouseMoved())
            rotateTheCannon();
        if (user.isMouseLeftClickPressed()) {
            shoot();
        }
        if (user.isMouseRightClickPressed())
            changeWeapon();
    }

    public void recieveGift(String giftType) {

    }



}
