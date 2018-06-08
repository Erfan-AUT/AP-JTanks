package game;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class testBlock {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.addKeyListener(new KeyAdapter() {
            long c = System.currentTimeMillis();
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(System.currentTimeMillis() - c);
                c = System.currentTimeMillis();
            }
        });
        while (true) {

        }
    }

}
