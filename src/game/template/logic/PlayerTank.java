package game.template.logic;

import game.template.Graphics.Animation;
import game.template.bufferstrategy.GameState;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;

public class PlayerTank extends Tan {

    private AffineTransform tx;
    private AffineTransformOp op;


    public PlayerTank(GameState state) {
        super(File.separator + "Move" + File.separator + "Tank");
        this.gameState = state;
        //rotationDegree = 90;

    }

    @Override


    @Override




}
