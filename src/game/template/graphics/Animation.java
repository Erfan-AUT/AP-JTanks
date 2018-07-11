package game.template.graphics;

import game.template.logic.Map;
import game.template.logic.cellfillers.Bullet;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Animation extends game.template.graphics.MasterAnimation {


    private boolean rotateIsNeeded;
    /**
     * State of animation. Is it still active or is it finished? We need this so that we can check and delete animation when is it finished.
     */
    public boolean active;

    private int movingRotationDeg;

    private AffineTransform tx;

    private double cannonRotationDeg;

    private BufferedImage gun;

    private ArrayList<Bullet> bullets;

    /**
     * Creates animation.
     *
     * @param animImage      Image of animation.
     * @param frameWidth     Width of the frame in animation image "animImage".
     * @param frameHeight    Height of the frame in animation image "animImage" - height of the animation image "animImage".
     * @param numberOfFrames Number of frames in the animation image.
     * @param frameTime      Amount of time that each frame will be shown before moving to the next one in milliseconds.
     * @param loop           Should animation repeat in loop?
     * @param x              x coordinate. Where to draw the animation on the screen?
     * @param y              y coordinate. Where to draw the animation on the screen?
     * @param showDelay      In milliseconds. How long to wait before starting the animation and displaying it?
     */
    public Animation(BufferedImage animImage, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, boolean loop, int x, int y, long showDelay) {
        super(animImage, frameWidth, frameHeight, numberOfFrames, frameTime, loop, x, y, showDelay);
    }

    public Animation(BufferedImage[] animImages, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, boolean loop, int x, int y, long showDelay) {
        super(animImages, frameWidth, frameHeight, numberOfFrames, frameTime, loop, x, y, showDelay);
        bullets = new ArrayList<>();
    }

    /**
     * Changes the coordinates of the animation.
     *
     * @param x x coordinate. Where to draw the animation on the screen?
     * @param y y coordinate. Where to draw the animation on the screen?
     */
    public void changeCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }


    private void updateImagesReverse() {
        if (timeForNextFrame <= System.currentTimeMillis()) {
            // Next frame.
            currentFrameNumber--;

            // If the animation is reached the end, we restart it by seting current frame to zero. If the animation isn't loop then we set that animation isn't active.
            if (currentFrameNumber < 0) {
                currentFrameNumber = numberOfFrames - 1;
                // If the animation isn't loop then we set that animation isn't active.
                if (!loop)
                    active = false;
            }

            // Starting and ending coordinates for cuting the current frame image out of the animation image.
            startingXOfFrameInImage = 0;
            endingXOfFrameInImage = frameWidth;

            // Set time for the next frame.
            startingFrameTime = System.currentTimeMillis();
            timeForNextFrame = startingFrameTime + frameTime;
        }
    }

    /**
     * Draws current frame of the animation.
     *
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d) {
        this.Update();

        // Checks if show delay is over.
        if (this.timeOfAnimationCration + this.showDelay <= System.currentTimeMillis())
            g2d.drawImage(animImage, x, y, x + frameWidth, y + frameHeight, startingXOfFrameInImage, 0, endingXOfFrameInImage, frameHeight, null);
    }

    public void drawImages(Graphics2D g2d) {
        UpdateImages();

        if (this.timeOfAnimationCration + this.showDelay <= System.currentTimeMillis())
            drawIt(g2d);
    }

    public void drawImagesReverse(Graphics2D g2d) {
        this.updateImagesReverse();

        if (this.timeOfAnimationCration + this.showDelay <= System.currentTimeMillis())
            drawIt(g2d);
    }

    public void drawOnlyTheCurrentFrame(Graphics2D g2d) {
        drawIt(g2d);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BufferedImage[] getAnimImages() {
        return animImages;
    }

    private void rot(int rotationDegree) {
        AffineTransform tx = AffineTransform.getTranslateInstance(150, 150);
        tx.rotate(Math.toRadians(rotationDegree));
    }

    public boolean isRotateIsNeeded() {
        return rotateIsNeeded;
    }

    public void setRotateIsNeeded(boolean rotateIsNeeded) {
        this.rotateIsNeeded = rotateIsNeeded;
    }

    public void setMovingRotationDeg(int rotationDeg) {
        this.movingRotationDeg = rotationDeg;
    }

    public void drawIt(Graphics2D g2d) {
        tx = AffineTransform.getTranslateInstance(x, y);
        tx.rotate(Math.toRadians(movingRotationDeg), 75, 75);
        g2d.drawImage(animImages[currentFrameNumber], tx, null);
        tx = AffineTransform.getTranslateInstance(x + 20, y + 12);
        tx.rotate(cannonRotationDeg, animImages[4].getWidth() / 2 - 20, animImages[4].getHeight() / 2);
        g2d.drawImage(gun, tx, null);
        //drawTheBullet(g2d);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void drawTheBullets(Graphics2D g2d) {
        for (Iterator it = bullets.iterator(); it.hasNext(); ) {
            Bullet bullet = (Bullet) it.next();
           if (bullet.isAlive()) {
                tx = AffineTransform.getTranslateInstance(bullet.locX, bullet.locY);
                tx.rotate(bullet.getDeg(), bullet.getBullet().getWidth() / 2, bullet.getBullet().getHeight() / 2);
                g2d.drawImage(bullet.getBullet(), tx, null);
            } else {
                it.remove();
               Map.bullets.remove(bullet);
            }
        }
    }

    public void setCannonRotationDeg(double cannonRotationDeg) {
        this.cannonRotationDeg = cannonRotationDeg;
    }


    @Override
    public boolean isActive() {
        return active;
    }

    public void setGun(BufferedImage gun) {
        this.gun = gun;
    }



}