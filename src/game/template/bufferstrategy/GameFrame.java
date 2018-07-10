/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.graphics.Animation;
import game.template.logic.cellfillers.Block;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.UserTank;

import java.awt.*;
import java.awt.image.BufferStrategy;
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

    public static final int GAME_HEIGHT = 1080;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
    private BufferStrategy bufferStrategy;
    Block wideAreaBlock;
    Block wideAreaBlock1;
    Block wideAreaBlock2;
    Block wideAreaBlock3;
    Block wideAreaBlock4;
    Block wideAreaBlock5;
    Block wideAreaBlock6;
    Block wideAreaBlock7;
    public static int x = 0;
    public static int y = -3600;

    public GameFrame(String title) {
        super(title);
        wideAreaBlock = new Block(30, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock1 = new Block(21, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock2 = new Block(12, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock3 = new Block(10, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock4 = new Block(15, 16, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock5 = new Block(9, 16, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock6 = new Block(1, 0, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        wideAreaBlock7 = new Block(1, 16, false, 0, null, 2, ".\\Stuffs\\WideArea.png", false, "WA");
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
//        setSize(GAME_WIDTH, GAME_HEIGHT);
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
        //TODO: should be visible ones.
//        Block block = new Block(5, 5, true, 0, null, 2, ".\\images\\HardWall.png", false, "nd");
//        block.getAnimation().drawIt(g2d);
        g2d.translate(x, y);
        wideAreaBlock.getAnimation().drawIt(g2d);
        wideAreaBlock1.getAnimation().drawIt(g2d);
        wideAreaBlock2.getAnimation().drawIt(g2d);
        wideAreaBlock3.getAnimation().drawIt(g2d);
        wideAreaBlock4.getAnimation().drawIt(g2d);
        wideAreaBlock5.getAnimation().drawIt(g2d);
        wideAreaBlock6.getAnimation().drawIt(g2d);
        wideAreaBlock7.getAnimation().drawIt(g2d);
        for (GameObject object : state.getMap().getAllObjects()) {
            if (!(object instanceof UserTank)) {
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
    }
}