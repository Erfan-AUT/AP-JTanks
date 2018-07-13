package com.company;

import java.awt.image.BufferedImage;

public class GameStuffs {

    private int x;
    private int y;
    private BufferedImage bufferedImage;
    private boolean thingsCanBePlacedOnIt;
    private String type;

    public GameStuffs(int x, int y, BufferedImage bufferedImage, boolean thingsCanBePlacedOnIt, String type) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.bufferedImage = bufferedImage;
        this.thingsCanBePlacedOnIt = thingsCanBePlacedOnIt;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public String getType() {
        return type;
    }
}
