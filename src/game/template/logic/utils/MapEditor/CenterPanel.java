package game.template.logic.utils.MapEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CenterPanel extends JPanel {
    BufferedImage background;
    private int cameraZeroX;
    private int cameraZeroY;
    private int mouseLastX;
    private int mouseLastY;
    private BufferedImage imageUnderTheMouse;
    private ArrayList<GameStuffs> stuffs;
    private boolean place;
    private String type;

    /**
     * Creates a new panel in which the map is created.
     */
    public CenterPanel() {
        type = null;
        requestFocus();
        place = false;
        cameraZeroX = 0;
        cameraZeroY = 0;
        mouseLastX = 0;
        mouseLastY = 0;
        imageUnderTheMouse = null;
        stuffs = new ArrayList<GameStuffs>();
        initInput();
        try {
            background = ImageIO.read(new File("Desert.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * paints the components in their respective places.
     * @param g g2d.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.translate(cameraZeroX, cameraZeroY);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                g2d.drawImage(background, null, cameraZeroX + 1800 * i, cameraZeroY + 1080 * j);
            }
        }
//        g2d.drawImage(background, null, cameraZeroX + 1800, cameraZeroY);
//        g2d.drawImage(background, null, cameraZeroX + 3600, cameraZeroY);
//        g2d.drawImage(background, null, cameraZeroX + 5400, cameraZeroY);

//        g2d.drawImage(background, null, 0, 0);
        for (GameStuffs gameStuffs : stuffs) {
            g2d.drawImage(gameStuffs.getBufferedImage(), null, gameStuffs.getX() * 120 + cameraZeroX, gameStuffs.getY() * 120 + cameraZeroY);
        }
        if (imageUnderTheMouse != null) {
            float opacity = 0.5f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.drawImage(imageUnderTheMouse, null, mouseLastX - imageUnderTheMouse.getWidth() / 2 - cameraZeroX, mouseLastY - imageUnderTheMouse.getHeight() / 2 - cameraZeroY);
        }
        repaint();
//        g2d.setStroke(new BasicStroke(3));
//        g2d.setColor(Color.BLACK);
//        for (int i = 1; i < 16; i++) {
//            g2d.drawLine(i * 120, 0, i * 120, 1920);
//        }
//        for (int j = 1; j < 16; j++) {
//            g2d.drawLine(0, j * 120, 1800, j * 120);
//        }
    }

    /**
     * initalizes the input that we should give to the app by mouse and keyboard movements.
     */

    private void initInput() {

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xMove = (e.getX() - mouseLastX);
                int yMove = (e.getY() - mouseLastY);
                cameraZeroX += xMove;
                cameraZeroY += yMove;
                if (cameraZeroX > 0) {
                    cameraZeroX -= xMove;
                }
                if (cameraZeroY > 0) {
                    cameraZeroY -= yMove;
                }
                if (cameraZeroX < -2626) {
                    cameraZeroX -= xMove;
                }
                if (cameraZeroY < -2640) {
                    cameraZeroY -= yMove;
                }

                mouseLastX = e.getX();
                mouseLastY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseLastX = e.getX();
                mouseLastY = e.getY();
            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (imageUnderTheMouse != null) {
                    boolean flag = true;
                    int x = (e.getX() - cameraZeroX * 2) / 120;
                    int y = (e.getY() - cameraZeroY * 2) / 120;
                    for (GameStuffs gameStuffs : stuffs) {
                        if ((gameStuffs.getX() == x && gameStuffs.getY() == y)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        stuffs.add(new GameStuffs(x, y, imageUnderTheMouse, place,type));
                    }
                } else {
                    try {
                        int x = (e.getX() - cameraZeroX * 2) / 120;
                        int y = (e.getY() - cameraZeroY * 2) / 120;
                        for (GameStuffs gameStuffs : stuffs) {
                            if ((gameStuffs.getX() == x && gameStuffs.getY() == y)) {
                                imageUnderTheMouse = gameStuffs.getBufferedImage();
                                stuffs.remove(gameStuffs);
                            }
                        }
                    } catch (Exception e1) {
                    }
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    save();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(0);
            }
        });

    }

    /**
     * saves the map in a persice location.
     */

    public void save() {
        String name = JOptionPane.showInputDialog("Please Enter a name");
        String st = name + ".txt";
        String string = "";
        for (int i = 0; i < stuffs.size(); i++) {
            if (i != stuffs.size() - 1) {
                string += "" + stuffs.get(i).getType() + "-" + stuffs.get(i).getY() + "-" + stuffs.get(i).getX() + " ";
            } else {
                string += "" + stuffs.get(i).getType() + "-" + stuffs.get(i).getY() + "-" + stuffs.get(i).getX() + " ";
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(st);
            char[] chars = string.toCharArray();
            for (char chars1 : chars) {
                fileOutputStream.write((int) chars1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the image that is under the mouse.
     * @param imageUnderTheMouse
     */

    public void setImageUnderTheMouse(BufferedImage imageUnderTheMouse) {
        this.imageUnderTheMouse = imageUnderTheMouse;
    }

    public BufferedImage getImageUnderTheMouse() {
        return imageUnderTheMouse;
    }

    public ArrayList<GameStuffs> getStuffs() {
        return stuffs;
    }

    public void setPlace(boolean place) {
        this.place = place;
    }

    public void setType(String type) {
        this.type = type;
    }
}
