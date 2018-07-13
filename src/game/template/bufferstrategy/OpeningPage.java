package game.template.bufferstrategy;


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
    private ArrayList<JRadioButton> buttons = new ArrayList<>();

    public OpeningPage() {
        super("WATCHA GONNA DO WHEN THE NORMAL TANKS RUN WILD ON YOU, BROTHER?");

        ButtonGroup initialGroup = new ButtonGroup();
        JRadioButton easyButton = new JRadioButton();
        JRadioButton mediumButton = new JRadioButton();
        JRadioButton hardButton = new JRadioButton();
        JPanel firstPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        initialGroup.add(easyButton);
        initialGroup.add(mediumButton);
        initialGroup.add(hardButton);
        easyButton.setSelected(true);
        KeyListener l = new KeyListener(firstPanel);
        for (Enumeration e = initialGroup.getElements(); e.hasMoreElements(); ) {
            JRadioButton button = (JRadioButton) e.nextElement();
            button.setBackground(Color.BLACK);
            button.addKeyListener(l);
            buttons.add(button);
            // buttons.add(button);
        }
        JLabel easyLabel = new JLabel("Easy.");
        JLabel mediumLabel = new JLabel("Medium");
        JLabel hardLabel = new JLabel("Hard");
        firstPanel.add(easyButton);
        firstPanel.add(easyLabel);
        firstPanel.add(mediumButton);
        firstPanel.add(mediumLabel);
        firstPanel.add(hardButton);
        firstPanel.add(hardLabel);
        firstPanel.setBackground(Color.BLACK);
        // firstPanel.addKeyListener(new KeyListener());
        add(firstPanel);
        setSize(500, 500);
        setVisible(true);
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
                case KeyEvent.VK_DOWN:
                    for (int i = 0; i < buttons.size(); i++) {
                        if (buttons.get(i).isSelected()) {
                            if (i != buttons.size() - 1)
                                buttons.get(i + 1).setSelected(true);
                            else
                                buttons.get(0).setSelected(true);
                            break;
                        }
                    }
                    break;
//
                case KeyEvent.VK_UP:
                    for (int i = 0; i < buttons.size(); i++) {
                        if (buttons.get(i).isSelected()) {
                            if (i != 0)
                                buttons.get(i - 1).setSelected(true);
                            else
                                buttons.get(buttons.size() - 1).setSelected(true);
                            break;
                        }
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    for (int i = 0; i < buttons.size(); i++)
                        if (buttons.get(i).isSelected())
                            level = i + 1;
                    currentPanel.removeAll();
                    currentPanel.repaint();
                    th.repaint();
                    break;
            }
        }
    }
}
