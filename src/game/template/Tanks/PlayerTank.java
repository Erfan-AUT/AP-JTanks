package game.template.Tanks;

import game.template.Bullet;
import game.template.Graphics.Animation;
import game.template.bufferstrategy.GameState;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerTank extends Tank {
    private GameState state;
    private BufferedImage[] cannons;
    private File cannonsLocation;
    private File[] cannonsImages;
    private BufferedImage[] rifles;
    private File riflesLocation;
    private File[] riflesImages;
    private int rotationDegree;

    public PlayerTank(GameState state) {
        super("./Move/Tank", "./Bullet/HeavyBullet.png");
        cannons = new BufferedImage[4];
        cannonsLocation = new File("./PlayerCannons");
        cannonsImages = cannonsLocation.listFiles();
        rifles = new BufferedImage[3];
        riflesLocation = new File("./PlayersRifles");
        riflesImages = riflesLocation.listFiles();
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
                } else {
                    rot(5);
                }
            } else {
                if (getAngle() == 0) {
                    setForward(true);
                    setVelocity(10);
                } else {
                    setForward(false);
                    setVelocity(10);
                }
            }
        } else if (state.isKeyLEFT() && !(state.isKeyUP() || state.isKeyDOWN())) {
            if (getAngle() != 0 && getAngle() != 180) {
                if (getAngle() < 180) {
                    rot(5);
                } else {
                    rot(-5);
                }
            } else {
                if (getAngle() == 0) {
                    setForward(false);
                    setVelocity(10);
                } else {
                    setForward(true);
                    setVelocity(10);
                }
            }
        } else if (state.isKeyUP() && !(state.isKeyRIGHT() || state.isKeyLEFT())) {
            if (getAngle() != 90 && getAngle() != 270) {
                if (getAngle() > 90 && getAngle() < 270) {
                    rot(5);
                } else {
                    rot(-5);
                }
            } else {
                if (getAngle() == 270) {
                    setForward(true);
                    setVelocity(10);
                } else {
                    setForward(false);
                    setVelocity(10);
                }
            }
        } else if (state.isKeyDOWN() && !(state.isKeyRIGHT() || state.isKeyLEFT())) {
            if (getAngle() != 90 && getAngle() != 270) {
                if (getAngle() > 90 && getAngle() < 270) {
                    rot(-5);
                } else {
                    rot(5);
                }
            } else {
                if (getAngle() == 90) {
                    setForward(true);
                    setVelocity(10);
                } else {
                    setForward(false);
                    setVelocity(10);
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

    private double rotateTheCannon() {
        int dx = state.getMouseX() - (getX() + 10);
        int dy = state.getMouseY() - (getY() + 20);
        double deg = Math.atan2(dy, dx);
        tankMove.setCannonRotationDeg(deg);
        return deg;
    }

    public void update() {
        rotate();
        move();
        double deg = rotateTheCannon();
        if (state.isMouseLeftClickPressed()) {
            Bullet bullet = shoot(deg);
            if (bullet != null) {
                tankMove.getBullets().add(bullet);
            }
        }
    }

    @Override
    protected Bullet shoot(double deg) {
//        int xSign = (int) (Math.cos(deg) / Math.abs(Math.cos(deg)));
//        if (((deg >= -Math.PI/2) && (deg <= Math.PI/2)))
//            xSign *= -1;
//        System.out.println(deg % 360);
        long time = System.currentTimeMillis();
        if (lastShootTime == 0 || time > lastShootTime + 1000) {
            lastShootTime = time;
            Bullet bullet = new Bullet(bulletImage, (int) (getX() + 67 + Math.cos(deg) * 100), (int) (getY() + 75 + Math.sin(deg) * (100)), Math.cos(deg), Math.sin(deg), deg);
            Thread thread = new Thread(bullet);
            thread.start();
            return bullet;
        }
        return null;
    }

    @Override
    protected void readContents() {
        super.readContents();
        for (int i = 0; i < cannonsImages.length; i++) {
            try {
                cannons[i] = ImageIO.read(cannonsImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < riflesImages.length; i++) {
            try {
                rifles[i] = ImageIO.read(riflesImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
