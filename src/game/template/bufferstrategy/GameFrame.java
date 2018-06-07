/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.Graphics.Animation;

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

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
    //    private BufferedImage expAnimImage;
//    private File expAnimImageLocation;
    private BufferStrategy bufferStrategy;
    private BufferedImage[] expAnimImages;
    private Animation expAnim;
    private File expAnimImageLocation;
    private File[] expAnimImageLocations;
    //    private Animation expAnim;
    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        //
        // Initialize the JFrame ...
        //
        expAnimImages = new BufferedImage[5];
        expAnimImageLocation = new File(".\\Move\\Etank");
        expAnimImageLocations = expAnimImageLocation.listFiles();
        for (int i = 0; i < 5; i++) {
            try {
                expAnimImages[i] = ImageIO.read(expAnimImageLocations[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        expAnim = new Animation(expAnimImages, 500, 500, 5, 50, true, 400, 400, 0);
//        expAnimImageLocation = new File("explosion_anim.png");
//        try {
//            expAnimImage = ImageIO.read(expAnimImageLocation);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        expAnim = new Animation(expAnimImage, 134, 134, 12, 20, true, 400, 400, 0);
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

        if (expAnim.active) {
            expAnim.drawImages(g2d);
        }
    }

}
