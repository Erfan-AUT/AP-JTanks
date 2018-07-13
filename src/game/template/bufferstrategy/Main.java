/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.logic.Map;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Program start.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Main {
	
    public static void main(String[] args) {
		// Initialize the global thread-pool
		ThreadPool.init();
		
		// Show the game menu ...
		
		// After the player clicks 'PLAY' ...
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
              //  Map map = new Map(1, null);
				OpeningPage openingPage = new OpeningPage();
//				GameFrame frame = new GameFrame("Game Title");
//				frame.setLocationRelativeTo(null); // put frame at center of screen
//				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				frame.setVisible(true);
//				frame.initBufferStrategy();
//				// Create and execute the game-loop
//				GameLoop game = new GameLoop(frame);
//				game.init();
//				ThreadPool.execute(game);
				// and the game starts ...
			}
		});
    }
}
