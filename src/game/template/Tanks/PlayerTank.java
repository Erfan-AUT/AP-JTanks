package game.template.Tanks;

import game.template.Graphics.Animation;
import game.template.bufferstrategy.GameState;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class PlayerTank extends Tank {
    private GameState state;
    private AffineTransform tx;
    private AffineTransformOp op;
    private int rotationDegree;

    public PlayerTank(GameState state) {
        super("./Move/00");
        this.state = state;
        rotationDegree = 90;
        setVelocity(10);
        setX(50);
        setY(50);
        setCannonX(getX() + 75);
        setCannonY(getY() + 75);
        readContents();
        tankMove = new Animation(tankImages, 250, 250, 4, 20, false, getX(), getY(), 0);
    }

    @Override
    public void move() {
        boolean isMoving = false;
        int x = getX();
        int y = getY();
        if (state.isKeyUP()) {
            y -= getVelocity();
            isMoving = true;
        } else if (state.isKeyDOWN()) {
            y += getVelocity();
            isMoving = true;
        }
        if (state.isKeyRIGHT()) {
            x += getVelocity();
            isMoving = true;
        } else if (state.isKeyLEFT()) {
            x -= getVelocity();
            isMoving = true;
        }
        setX(x);
        setY(y);
        setCannonX(getX() + 75);
        setCannonY(getY() + 75);
        tankMove.changeCoordinates(getX(), getY());
        if (isMoving) {
            tankMove.setActive(true);
        } else {
            tankMove.setActive(false);
        }
    }

    @Override
    public void rotate() {
        int tmp = getAngle() % 360;
        int x = getX();
        int y = getY();
        boolean isMoving = false;
        if (tmp < 0) {
            setAngle(360 + tmp);
        } else {
            setAngle(tmp);
        }

        if (state.isKeyUP() && state.isKeyRIGHT() || state.isKeyRIGHT() && state.isKeyUP()) {
            if (getAngle() >= 180 && getAngle() < 305) {
                crossRot(5);
            } else if (getAngle() == 305) {
                setVelocity(10);
            } else {
                crossRot(-5);
            }
        } else if (state.isKeyUP() && state.isKeyLEFT() || state.isKeyLEFT() && state.isKeyUP()) {
            if ((getAngle() <= 360 && getAngle() > 215) || getAngle() == 0) {
                crossRot(-5);
            } else if (getAngle() == 215) {
                setVelocity(10);
            } else {
                crossRot(5);
            }
        } else if (state.isKeyDOWN() && state.isKeyLEFT()) {
            if (getAngle() >= 0 && getAngle() < 135) {
                crossRot(5);
            } else if (getAngle() == 135) {
                setVelocity(10);
            } else {
                crossRot(-5);
            }
        } else if (state.isKeyDOWN() && state.isKeyRIGHT()) {
            if (getAngle() <= 180 && getAngle() > 45) {
                crossRot(-5);
            } else if (getAngle() == 45) {
                setVelocity(10);
            } else {
                crossRot(5);
            }
        }
        if (state.isKeyRIGHT() && !(state.isKeyUP() || state.isKeyDOWN())) {
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
        } else if (state.isKeyLEFT() && !(state.isKeyUP() || state.isKeyDOWN())) {
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
        } else if (state.isKeyUP() && !(state.isKeyRIGHT() || state.isKeyLEFT())) {
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
        } else if (state.isKeyDOWN() && !(state.isKeyRIGHT() || state.isKeyLEFT())) {
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
        tankMove.setMovingRotationDeg(getAngle());
    }

    private void crossRot(int deg) {
        setForward(false);
        setVelocity(0);
        rotationDegree = deg;
        setAngle(getAngle() + rotationDegree);
        tankMove.setMovingRotationDeg(getAngle());
    }

    private void rotateTheCannon() {
        int dx = state.getMouseX() - (getX() + 10);
        int dy = state.getMouseY() - (getY() + 20);
        double deg = Math.atan2(dy, dx);
        tankMove.setCannonRotationDeg(deg);
    }

    public void update() {
        rotate();
        move();
        rotateTheCannon();
    }

}
