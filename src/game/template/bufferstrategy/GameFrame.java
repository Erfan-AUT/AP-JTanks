/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.graphics.Animation;
import game.template.graphics.MasterAnimation;
import game.template.logic.Map;
import game.template.logic.cellfillers.*;
import game.template.logic.utils.FileUtils;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;

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
    private Block wideAreaBlock;
    private GameState state;
    private BufferedImage cannonsCount;
    private BufferedImage riflesCount;
    private BufferedImage healthImage;
    private GameLoop game;
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
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(".\\Images\\gunPoint.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(),
                this.getY()), "img");
        this.setCursor(c);
        try {
            cannonsCount = ImageIO.read(new File("." + File.separator +
                    "Images" + File.separator + "NumberOfHeavyBullet2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            riflesCount = ImageIO.read(new File("." + File.separator +
                    "Images" + File.separator + "NumberOfMachinGun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            healthImage = ImageIO.read(new File("." + File.separator +
                    "Images" + File.separator + "health.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        wideAreaBlock = new Block(0, 0, false, 0, null, 2,
                "." + File.separator + "Stuffs" +
                        File.separator + "WideArea.png", false, "WA");
        addWindowListener(new WinListener(this));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Initialize the JFrame ...
    }

    public void setGame(GameLoop game) {
        this.game = game;
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        setVisible(true);
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
        //setVisible(false);
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
        this.state = state;
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        wideAreaBlock.getAnimation().drawIt(g2d);
        ArrayList<UserTank> tanks = new ArrayList<>();
        int user = 0;
        if (state.getMap() != null) {
            tanks = state.getMap().getMainTanks();
            user = state.getUser();
            g2d.translate(-state.getMap().getCameraZeroX(user), -state.getMap().getCameraZeroY(user));
            //TODO: should be visible ones.
            try {
                for (GameObject object : state.getMap().getVisibleObjects()) {
                    if (!(object instanceof Tank)) {
                        // if (!(object instanceof ComputerTank) && !(object instanceof UserTank)) {
                        if (object.isAlive())
                            object.getAnimation().drawIt(g2d);
                    } else if (object instanceof ComputerTank) {
                        if (object.isAlive()) {
                            Tank tank = ((Tank) object);
                            ((Animation) tank.getAnimation()).drawTheBullets(g2d);
                            //                tank.getAnimation().drawIt(g2d);
                            if (tank.getAnimation().active) {
                                if (tank.isForward()) {
                                    tank.getAnimation().drawImages(g2d);
                                } else {
                                    ((Animation) tank.getAnimation()).drawImagesReverse(g2d);
                                }
                            } else {
                                ((Animation) tank.getAnimation()).drawOnlyTheCurrentFrame(g2d);
                            }
                        } else {
                            state.getMap().getAllObjects().remove(object);
                        }
                    }
                }
            } catch (Exception e) {
                //  e.printStackTrace();
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
            try {
                for (Iterator it = Map.explosions.iterator(); it.hasNext(); ) {
                    MasterAnimation masterAnimation = (MasterAnimation) it.next();
                    if (masterAnimation.isActive()) {
                        masterAnimation.Draw(g2d);
                    } else {
                        Map.explosions.remove(masterAnimation);
                    }
                }
            } catch (ConcurrentModificationException ex) {

            }
            g2d.setFont(new Font("SansSerif", Font.BOLD, 50));
            g2d.setColor(Color.GREEN);
            g2d.drawString("" + tanks.get(user).getRifleCount(), state.getMap().getCameraZeroX(0) + 100, state.getMap().getCameraZeroY(0) + 100);
            g2d.drawString("" + tanks.get(user).getCannonCount(), state.getMap().getCameraZeroX(0) + 100, state.getMap().getCameraZeroY(user) + 160);
            g2d.drawImage(riflesCount, null, state.getMap().getCameraZeroX(0) + 25, state.getMap().getCameraZeroY(0) + 50);
            g2d.drawImage(cannonsCount, null, state.getMap().getCameraZeroX(0) + 35, state.getMap().getCameraZeroY(0) + 120);
            int xPlus = 36;
            for (int i = 0; i < ((UserTank) state.getPlayerTank()).getLives(); i++) {
                g2d.drawImage(healthImage, null, state.getMap().getCameraZeroX(0) + 450 + +(i + 1) * xPlus, state.getMap().getCameraZeroY(0) + 50);
            }
        }
//        int drawX, drawY;
//        g2d.drawString("Cannon Count: " + tanks.get(user).getCannonCount(),
//                100, 100 );
//        g2d.drawString("Rifle Count: " + tanks.get(user).getRifleCount(),
//                100, 100);
        //g2d.translate(pTank.locX, pTank.locY);
    }


    class WinListener extends WindowAdapter {
        public WinListener(JFrame frame) {
            this.frame = frame;
        }

        JFrame frame;

        @Override
        public void windowClosing(WindowEvent e) {
            int i = JOptionPane.showConfirmDialog(frame, "Would you like to save the game or not?");
            switch (i) {
                case JOptionPane.YES_OPTION:
                    FileUtils.writeMap(state.getMap(), ".\\maps\\savedMap.txt");
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    game.setGameOver(true);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }

        }
    }
}


//        if (expAnim.active) {
//            expAnim.drawImages(g2d);
//        } else {
//            expAnim.drawOnlyTheCurrentFrame(g2d);
//        }

