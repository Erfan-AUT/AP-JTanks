package game.template.bufferstrategy;


import javax.jnlp.JNLPRandomAccessFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class OpeningPage extends JFrame {

    private int level;
    // private ArrayList<JRadioButton> buttons = new ArrayList<>()
    private JFrame th = this;
    private ArrayList<JRadioButton> buttons;
    JRadioButton editMapBeforePlaying = new JRadioButton();
    private KeyListener l;
    //private ArrayList<JComponent> components;

    public OpeningPage() {
        super("WATCHA GONNA DO WHEN THE NORMAL TANKS RUN WILD ON YOU, BROTHER?");
        //Move All of this into init.
        init();
//        for (JComponent component : components)
//            firstPanel.add(component);

        // firstPanel.addKeyListener(new KeyListener());
        setSize(500, 500);
        setVisible(true);
    }

    private void init() {
        JPanel firstPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        buttons = new ArrayList<>();
        //components = new ArrayList<>();
        l = new KeyListener(firstPanel);
        JRadioButton resumeOldGame = new JRadioButton();
        JRadioButton playAsServer = new JRadioButton();
        JRadioButton playAsClient = new JRadioButton();
        JRadioButton playSolo = new JRadioButton();
        resumeOldGame.setSelected(true
        );

        JLabel resumeLabel = new JLabel("Resume old game.");
        JLabel playAsServerLabel = new JLabel("Play network game as server.");
        JLabel playAsClientLabel = new JLabel("Play network game as client.");
        JLabel playSoloLabel = new JLabel("Play locally and solo.");
        JLabel mapEditorLabel = new JLabel("Edit maps before you play.");
        ButtonGroup zeroGroup = new ButtonGroup();
        zeroGroup.add(resumeOldGame);
        zeroGroup.add(playAsServer);
        zeroGroup.add(playAsClient);
        zeroGroup.add(playSolo);
        zeroGroup.add(editMapBeforePlaying);
        firstPanel.add(resumeOldGame);
        firstPanel.add(resumeLabel);
        firstPanel.add(playAsServer);
        firstPanel.add(playAsServerLabel);
        firstPanel.add(playAsClient);
        firstPanel.add(playAsClientLabel);
        firstPanel.add(playSolo);
        firstPanel.add(playSoloLabel);
        firstPanel.add(editMapBeforePlaying);
        firstPanel.add(mapEditorLabel);
        enumerate(zeroGroup);
        firstPanel.setBackground(Color.BLACK);
        add(firstPanel);
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


    class KeyListener extends KeyAdapter {
        JPanel currentPanel;
        int setCount = 0;

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
                        if (!editMapBeforePlaying.isSelected()) {
                            ButtonGroup initialGroup = new ButtonGroup();
                            JRadioButton easyButton = new JRadioButton();
                            JRadioButton mediumButton = new JRadioButton();
                            JRadioButton hardButton = new JRadioButton();
                            JLabel easyLabel = new JLabel("Easy.");
                            JLabel mediumLabel = new JLabel("Medium");
                            JLabel hardLabel = new JLabel("Hard");
                            initialGroup.add(easyButton);
                            initialGroup.add(mediumButton);
                            initialGroup.add(hardButton);
                            easyButton.setSelected(true);
                            enumerate(initialGroup);
                            currentPanel.add(easyButton);
                            currentPanel.add(easyLabel);
                            currentPanel.add(mediumButton);
                            currentPanel.add(mediumLabel);
                            currentPanel.add(hardButton);
                            currentPanel.add(hardLabel);
                            easyButton.requestFocus();
                            //    currentPanel.repaint();
                        }
                    }
                    if (setCount == 1) {
                        for (int i = 0; i < buttons.size(); i++)
                            if (buttons.get(i).isSelected())
                                level = i + 1;

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
    }
}
