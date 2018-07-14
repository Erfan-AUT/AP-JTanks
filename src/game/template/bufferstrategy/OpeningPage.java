package game.template.bufferstrategy;


import game.template.logic.utils.MapEditor.EditorMain;
import game.template.logic.utils.Music;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class OpeningPage extends JFrame implements Runnable {

    private int level;
    private String mapName;
    // private ArrayList<JRadioButton> buttons = new ArrayList<>()
    private JFrame th = this;
    private ArrayList<JRadioButton> buttons;
    public static boolean isOnNetwork;
    public static boolean trueForServerFalseForClient;
    public static boolean trueForSoloFalseForSaved = true;
    private boolean isGameStarted = false;
    private JRadioButton editMapBeforePlaying = new JRadioButton("Edit maps before you play.");
    private JRadioButton resumeOldGame = new JRadioButton("Resume old game.");
    private JRadioButton playAsServer = new JRadioButton("Play network game as server.");
    private JRadioButton playAsClient = new JRadioButton("Play network game as client.");
    private JRadioButton playSolo = new JRadioButton("Play locally and solo.");
    private OpeningPage op;
    private GameLoop game;
    private GameFrame frame;
    private Music music;
    //private Runnable main;
    private KeyListener l;
    //private ArrayList<JComponent> components;

    public OpeningPage(GameLoop game, GameFrame frame) {
        super("WATCHA GONNA DO WHEN THE NORMAL TANKS RUN WILD ON YOU, BROTHER?");
        this.frame = frame;
        this.game = game;
        setSize(500, 500);
        setVisible(true);
        op = this;
        frame.setVisible(false);
        game.setGameOver(true);
    }

    private void init() {
        JPanel firstPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        buttons = new ArrayList<>();
        //components = new ArrayList<>();
        l = new KeyListener(firstPanel);

        resumeOldGame.setSelected(true);
        long time = System.currentTimeMillis();
        music = new Music("." + File.separator + "Sounds" + File.separator + "gameSound1.mp3", true);
        ThreadPool.execute(music);
//        JLabel resumeLabel = new JLabel();
//        JLabel playAsServerLabel = new JLabel();
//        JLabel playAsClientLabel = new JLabel();
//        JLabel playSoloLabel = new JLabel();
//        JLabel mapEditorLabel = new JLabel();
        ButtonGroup zeroGroup = new ButtonGroup();
        zeroGroup.add(resumeOldGame);
        zeroGroup.add(playAsServer);
        zeroGroup.add(playAsClient);
        zeroGroup.add(playSolo);
        zeroGroup.add(editMapBeforePlaying);
        firstPanel.add(resumeOldGame);
        //firstPanel.add(resumeLabel);
        firstPanel.add(playAsServer);
        //firstPanel.add(playAsServerLabel);
        firstPanel.add(playAsClient);
        //firstPanel.add(playAsClientLabel);
        firstPanel.add(playSolo);
        //firstPanel.add(playSoloLabel);
        firstPanel.add(editMapBeforePlaying);
        //firstPanel.add(mapEditorLabel);
        enumerate(zeroGroup);
        firstPanel.setBackground(Color.BLACK);
        add(firstPanel);
        firstPanel.repaint();
        firstPanel.revalidate();
        this.repaint();
        this.revalidate();
        resumeOldGame.requestFocus();
    }

    private void enumerate(ButtonGroup group) {
        buttons.clear();
        for (Enumeration e = group.getElements(); e.hasMoreElements(); ) {
            JRadioButton button = (JRadioButton) e.nextElement();
            button.setBackground(Color.BLACK);
            button.addKeyListener(l);
            buttons.add(button);
            // buttons.add(button);
        }
    }

    @Override
    public void run() {
        init();
        //while (!isGameStarted);
    }


    class KeyListener extends KeyAdapter {
        JPanel currentPanel;
        double setCount = 0;
        ArrayList<JRadioButton> mapButtons = new ArrayList<>();

        public KeyListener(JPanel currentPanel) {
            this.currentPanel = currentPanel;
        }

        public void setCurrentPanel(JPanel currentPanel) {
            this.currentPanel = currentPanel;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_ENTER:
                    currentPanel.removeAll();
                    if (setCount == 0) {
                        if ((!editMapBeforePlaying.isSelected()) && (!resumeOldGame.isSelected())
                                && (!playAsClient.isSelected())) {
                            ButtonGroup mapGroup = new ButtonGroup();

                            ArrayList<String> distinctMapNames = new ArrayList<>();
                            File mapDirectory = new File("." + File.separator + "maps" + File.separator + "defaultMaps");
                            for (File map : mapDirectory.listFiles()) {
                                String buttonName = "";
                                for (char c : map.getName().toCharArray()) {
                                    if (!Character.isDigit(c))
                                        buttonName += c;
                                    else
                                        break;
                                }
                                boolean isNew = true;
                                for (String name : distinctMapNames)
                                    if (name.equals(buttonName))
                                        isNew = false;
                                if (isNew) {
                                    JRadioButton button = new JRadioButton(buttonName);
                                    distinctMapNames.add(buttonName);
                                    mapButtons.add(button);
                                    mapGroup.add(button);
                                    currentPanel.add(button);
                                    currentPanel.revalidate();
                                    currentPanel.repaint();
                                }
                            }
                            mapButtons.get(0).requestFocus();
                            enumerate(mapGroup);
                            //    currentPanel.repaint();
                        } else if (resumeOldGame.isSelected()) {
                            trueForSoloFalseForSaved = false;
                            dispose();

                        } else if (playAsClient.isSelected()) {
                            trueForServerFalseForClient = false;
                            isOnNetwork = true;
                        } else if (playAsServer.isSelected()) {
                            isOnNetwork = true;
                            trueForServerFalseForClient = true;
                        } else if (editMapBeforePlaying.isSelected()) {
                            Thread t = new Thread(new EditorMain());
                            t.start();
                            music.close();
                            op.dispose();
                        }
                    }
                    if (setCount == 1)
                    {
                        for (JRadioButton radioButton : mapButtons)
                            if (radioButton.isSelected())
                                mapName = radioButton.getText();
                        showLevelButtons();
                    }

                    if (setCount == 2) {
                        for (int i = 0; i < buttons.size(); i++)
                            if (buttons.get(i).isSelected())
                                level = i + 1;
                        dispose();
                    }

                    setCount++;
                    th.remove(currentPanel);
                    currentPanel.repaint();
                    th.add(currentPanel);
                    th.repaint();
                    th.revalidate();
                    currentPanel.revalidate();
                    //  th.requestFocus();
                    break;
            }
        }

        private void showLevelButtons() {
            ButtonGroup initialGroup = new ButtonGroup();
            JRadioButton easyButton = new JRadioButton("Easy.");
            JRadioButton mediumButton = new JRadioButton("Medium");
            JRadioButton hardButton = new JRadioButton("Hard.");
//            JLabel easyLabel = new JLabel("Easy.");
//            JLabel mediumLabel = new JLabel("Medium");
//            JLabel hardLabel = new JLabel("Hard");
            initialGroup.add(easyButton);
            initialGroup.add(mediumButton);
            initialGroup.add(hardButton);
            easyButton.setSelected(true);
            enumerate(initialGroup);
            currentPanel.add(easyButton);
            //currentPanel.add(easyLabel);
            currentPanel.add(mediumButton);
            //currentPanel.add(mediumLabel);
            currentPanel.add(hardButton);
            // currentPanel.add(hardLabel);
            easyButton.requestFocus();
        }


        private void dispose() {
            op.dispose();
            isGameStarted = true;
            game.setGameOver(false);
            game.init();
            ThreadPool.execute(game);
            frame.setVisible(true);
            music.close();
            game.getState().setGame(game);
        }
    }
}
