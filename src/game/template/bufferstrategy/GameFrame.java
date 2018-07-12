/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.graphics.Animation;
import game.template.logic.Map;
import game.template.logic.cellfillers.Block;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.UserTank;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This structure uses the modern BufferStrategy approach for
 * double-buffering; actually, it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 1080;                  // 1080p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
    //    private BufferedImage expAnimImage;
//    private File expAnimImageLocation;
    Block wideAreaBlock;
//    Block wideAreaBlock1;
//    Block wideAreaBlock2;
//    Block wideAreaBlock3;
//    Block wideAreaBlock4;
//    Block wideAreaBlock5;
//    Block wideAreaBlock6;
//    Block wideAreaBlock7;
    boolean first = true;
    private BufferStrategy bufferStrategy;
//    private BufferedImage[] expAnimImages;
//    private Animation expAnim;
//    private File expAnimImageLocation;
//    private File[] expAnimImageLocations;
    private int user;

    //    private Animation expAnim;
    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        wideAreaBlock = new Block(0, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        // wideAreaBlock.readContents();
//        wideAreaBlock1 = new Block(21, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
//        wideAreaBlock2 = new Block(12, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
//        wideAreaBlock3 = new Block(10, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
//        wideAreaBlock4 = new Block(15, 16, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
//        wideAreaBlock5 = new Block(9, 16, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
//        wideAreaBlock6 = new Block(1, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
//        wideAreaBlock7 = new Block(1, 16, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        //
        // Initialize the JFrame ...
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Get a new graphics context to render the current frame
        try {
            Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
            try {
                // Do the rendering
                doRendering(graphics, state);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // Dispose the graphics, because it is no more needed
                graphics.dispose();
            }
            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        //
        // Draw all game elements according
        //  to the game 'state' using 'g2d' ...
        //
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        wideAreaBlock.getAnimation().drawIt(g2d);
//        wideAreaBlock1.getAnimation().drawIt(g2d);
//        wideAreaBlock2.getAnimation().drawIt(g2d);
//        wideAreaBlock3.getAnimation().drawIt(g2d);
//        wideAreaBlock4.getAnimation().drawIt(g2d);
//        wideAreaBlock5.getAnimation().drawIt(g2d);
//        wideAreaBlock6.getAnimation().drawIt(g2d);
//        wideAreaBlock7.getAnimation().drawIt(g2d);

//        if (first) {
//            g2d.translate(0, -3600);
//            first = false;
//        } else;
       // user = state.getMap().get
        g2d.translate(-state.getMap().getCameraZeroX(0), -state.getMap().getCameraZeroY(0));
        //TODO: should be visible ones.
        for (GameObject object : state.getMap().getVisibleObjects()) {
            if (!(object instanceof UserTank)) {
                if (object.isAlive())
                    object.getAnimation().drawIt(g2d);
            }
        }
        UserTank pTank = (UserTank) state.getPlayerTank();
        if (pTank.getAnimation().isActive()) {
            if (pTank.isForward()) {
                pTank.getAnimation().drawImages(g2d);
            } else {
                ((Animation) pTank.getAnimation()).drawImagesReverse(g2d);
            }
        } else {
            ((Animation) state.getPlayerTank().getAnimation()).drawOnlyTheCurrentFrame(g2d);
        }
        ((Animation) pTank.getAnimation()).drawTheBullets(g2d);
        //g2d.translate(pTank.locX, pTank.locY);
    }
}


//        if (expAnim.active) {
//            expAnim.drawImages(g2d);
//        } else {
//            expAnim.drawOnlyTheCurrentFrame(g2d);
//        }

