package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map extends JFrame {
    private JPanel mainJPanle;
    private CenterPanel centerPanel;
    private JToolBar toolBar;
    private ArrayList<JButton> buttonsOfObjects;
    private ArrayList<BufferedImage> imagesOfObjects;
    private ButtonHandler buttonHandler;

    public Map() throws HeadlessException {
        buttonsOfObjects = new ArrayList<JButton>();
        imagesOfObjects = new ArrayList<BufferedImage>();
        buttonHandler = new ButtonHandler();
        configure();
    }

    private void configure() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        initMainPanel();
        initCenterPanel();
        initObjectImages();
        initButtons();
        initToolBar();
        setContentPane(mainJPanle);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_S) {
                            centerPanel.save();
                        }
                    }
                });
            }
        });
    }

    private void initMainPanel() {
        mainJPanle = new JPanel();
        mainJPanle.setLayout(new BorderLayout());
    }

    private void initCenterPanel() {
        centerPanel = new CenterPanel();
        mainJPanle.add(centerPanel, BorderLayout.CENTER);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setLayout(new GridLayout(buttonsOfObjects.size() + 1, 1));
        toolBar.setFloatable(false);
        mainJPanle.add(toolBar, BorderLayout.EAST);
        for (JButton button : buttonsOfObjects) {
            toolBar.add(button);
        }
        JButton save = new JButton("Save");
        save.addActionListener(buttonHandler);
        toolBar.add(save);
    }

    private void initButtons() {
        File nailsDir = new File(".\\Nails");
        File[] nails = nailsDir.listFiles();
        for (File file : nails) {
            JButton button = new JButton();
            button.addActionListener(buttonHandler);
            button.setActionCommand(String.valueOf(file).substring(8, String.valueOf(file).lastIndexOf('.')));
            button.setContentAreaFilled(false);
            button.setIcon(new ImageIcon(String.valueOf(file)));
            buttonsOfObjects.add(button);
        }
    }

    private void initObjectImages() {
        File objects = new File(".\\Objects");
        File[] imagesOfObjects = objects.listFiles();
        for (File file : imagesOfObjects) {
            try {
                this.imagesOfObjects.add(ImageIO.read(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonHandler implements ActionListener {
        public ButtonHandler() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            centerPanel.setPlace(false);
            if (e.getActionCommand().equals("PlayerTank")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(0));
                centerPanel.setType("u");
            } else if (e.getActionCommand().equals("00")) {
                centerPanel.setImageUnderTheMouse(null);
                centerPanel.setType(null);
            } else if (e.getActionCommand().equals("Enemy1")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(1));
                centerPanel.setType("c2");
            } else if (e.getActionCommand().equals("Enemy2")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(2));
                centerPanel.setType("c1");
            } else if (e.getActionCommand().equals("Enemy3")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(3));
                centerPanel.setType("c3");
            } else if (e.getActionCommand().equals("Enemy4")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(4));
                centerPanel.setType("r");
            } else if (e.getActionCommand().equals("HardWall")) {
                centerPanel.setPlace(true);
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(5));
                centerPanel.setType("nd");
            } else if (e.getActionCommand().equals("SoftWall")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(6));
                centerPanel.setType("d");
            } else if (e.getActionCommand().equals("Plant")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(7));
                centerPanel.setType("p");
            } else if (e.getActionCommand().equals("CannonRefill")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(8));
                centerPanel.setType("cf");
            } else if (e.getActionCommand().equals("RifleRefill")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(9));
                centerPanel.setType("rf");
            } else if (e.getActionCommand().equals("Repair")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(10));
                centerPanel.setType("re");
            } else if (e.getActionCommand().equals("Teazel")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(11));
                centerPanel.setType("t1");
            } else if (e.getActionCommand().equals("Upgrade")) {
                centerPanel.setImageUnderTheMouse(imagesOfObjects.get(12));
                centerPanel.setType("up");
            } else if (e.getActionCommand().equals("Save")) {
                centerPanel.save();
            }
        }
    }

}
