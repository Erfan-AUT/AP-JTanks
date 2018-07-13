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
    ButtonGroup initialGroup = new ButtonGroup();
    JRadioButton easyButton = new JRadioButton();
    JRadioButton mediumButton = new JRadioButton();
    JRadioButton hardButton = new JRadioButton();
    JPanel firstPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    JFrame th;

    public OpeningPage() {
        super("WATCHA GONNA DO WHEN THE NORMAL TANKS RUN WILD ON YOU, BROTHER?");


        initialGroup.add(easyButton);
        initialGroup.add(mediumButton);
        initialGroup.add(hardButton);
        easyButton.setSelected(true);
        for (Enumeration e = initialGroup.getElements(); e.hasMoreElements(); ) {
            JRadioButton button = (JRadioButton) e.nextElement();
            button.setBackground(Color.BLACK);
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
        firstPanel.addKeyListener(new KeyListener());
        add(firstPanel);
        setSize(500, 500);
        setVisible(true);
        th = this;
    }

    class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    if (hardButton.isSelected()) {
                        easyButton.setSelected(true);
                        break;
                    }
                    if (easyButton.isSelected()) {
                        mediumButton.setSelected(true);
                        break;
                    }

                    if (mediumButton.isSelected()) {
                        hardButton.setSelected(true);
                        break;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (hardButton.isSelected()) {
                        mediumButton.setSelected(true);
                        break;
                    }
                    if (easyButton.isSelected()) {
                        hardButton.setSelected(true);
                        break;
                    }

                    if (mediumButton.isSelected()) {
                        easyButton.setSelected(true);
                        break;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    System.out.println("AAA");
                    firstPanel.removeAll();
                    th.remove(firstPanel);
                    th.revalidate();
                    break;
            }
        }
    }
}
