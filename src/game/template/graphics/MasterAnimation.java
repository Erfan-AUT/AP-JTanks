package game.template.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class MasterAnimation {

    // Image of animation.
    protected BufferedImage animImage;

    protected BufferedImage[] animImages;

    // Width of one frame in animated image.
    protected int frameWidth;

    // Height of the frame(image).
    protected int frameHeight;

    // Number of frames in the animation image.
    protected int numberOfFrames;

    // Amount of time between frames in milliseconds. (How many time in milliseconds will be one frame shown before showing next frame?)
    protected long frameTime;

    // Time when the frame started showing. (We use this to calculate the time for the next frame.)
    protected long startingFrameTime;

    // Time when we show next frame. (When current time is equal or greater than time in "timeForNextFrame", it's time to move to the next frame of the animation.)
    protected long timeForNextFrame;

    // Current frame number.
    protected int currentFrameNumber;

    // Should animation repeat in loop?
    protected boolean loop;

    /**
     * x and y coordinates. Where to draw the animation on the screen?
     */
    public int x;
    public int y;

    // Starting x coordinate of the current frame in the animation image.
    protected int startingXOfFrameInImage;

    // Ending x coordinate of the current frame in the animation image.
    protected int endingXOfFrameInImage;

    /**
     * State of animation. Is it still active or is it finished? We need this so that we can check and delete animation when is it finished.
     */
    public boolean active;

    // In milliseconds. How long to wait before starting the animation and displaying it?
    protected long showDelay;

    // At what time was animation created.
    protected long timeOfAnimationCration;


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
    public MasterAnimation(BufferedImage animImage, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, boolean loop, int x, int y, long showDelay) {
        this.animImage = animImage;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numberOfFrames = numberOfFrames;
        this.frameTime = frameTime;
        this.loop = loop;

        this.x = x;
        this.y = y;

        this.showDelay = showDelay;

        timeOfAnimationCration = System.currentTimeMillis();
        startingXOfFrameInImage = 0;
        endingXOfFrameInImage = frameWidth;
        startingFrameTime = System.currentTimeMillis() + showDelay;
        timeForNextFrame = startingFrameTime + this.frameTime;
        currentFrameNumber = 0;
        active = true;
    }

    public MasterAnimation(BufferedImage[] animImages, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, boolean loop, int x, int y, long showDelay) {
        this.animImages = animImages;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numberOfFrames = numberOfFrames;
        this.frameTime = frameTime;
        this.loop = loop;


        this.x = x;
        this.y = y;

        this.showDelay = showDelay;

        timeOfAnimationCration = System.currentTimeMillis();

        startingXOfFrameInImage = 0;
        endingXOfFrameInImage = frameWidth;

        startingFrameTime = System.currentTimeMillis() + showDelay;
        timeForNextFrame = startingFrameTime + this.frameTime;
        currentFrameNumber = 0;
        active = true;
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


    /**
     * It checks if it's time to show next frame of the animation.
     * It also checks if the animation is finished.
     */
    protected void Update() {
        if (timeForNextFrame <= System.currentTimeMillis()) {
            // Next frame.
            currentFrameNumber++;

            // If the animation is reached the end, we restart it by seting current frame to zero. If the animation isn't loop then we set that animation isn't active.
            if (currentFrameNumber >= numberOfFrames) {
                currentFrameNumber = 0;

                // If the animation isn't loop then we set that animation isn't active.
                if (!loop)
                    active = false;
            }

            // Starting and ending coordinates for cuting the current frame image out of the animation image.
            startingXOfFrameInImage = currentFrameNumber * frameWidth;
            endingXOfFrameInImage = startingXOfFrameInImage + frameWidth;

            // Set time for the next frame.
            startingFrameTime = System.currentTimeMillis();
            timeForNextFrame = startingFrameTime + frameTime;
        }
    }

    protected void UpdateImages() {
        if (timeForNextFrame <= System.currentTimeMillis()) {
            // Next frame.
            currentFrameNumber++;

            // If the animation is reached the end, we restart it by seting current frame to zero. If the animation isn't loop then we set that animation isn't active.
            if (currentFrameNumber >= numberOfFrames) {
                currentFrameNumber = 0;

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
        this.UpdateImages();

        if (this.timeOfAnimationCration + this.showDelay <= System.currentTimeMillis())
            g2d.drawImage(animImages[currentFrameNumber], x, y, x + frameWidth, y + frameHeight, startingXOfFrameInImage, 0, endingXOfFrameInImage, frameHeight, null);

    }

    public void drawIt(Graphics2D g2d) {
       // g2d.drawImage(animImages[currentFrameNumber], x, y, x + frameWidth, y + frameHeight, startingXOfFrameInImage, 0, endingXOfFrameInImage, frameHeight, null);
        g2d.drawImage(animImages[currentFrameNumber], x, y, null);
    }

    public boolean isActive() {
        return active;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }
}
