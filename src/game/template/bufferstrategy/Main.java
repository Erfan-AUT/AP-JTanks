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

//				Thread thread = new Thread(openingPage);
//                try {
//                    thread.run();
//                    thread.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ThreadPool.execute(openingPage);
//                openingPage
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                GameFrame frame = new GameFrame("Game Title");
//                Thread t = new Thread(openingPage);
//                try {
//                    t.start();
//                    t.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                //System.out.println(this.getClass());

//                GameFrame.playMusic("");
				frame.setLocationRelativeTo(null); // put frame at center of screen
				//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(false);
				frame.initBufferStrategy();
				// Create and execute the game-loop
				GameLoop game = new GameLoop(frame);
				OpeningPage openingPage = new OpeningPage(game, frame);
				Thread t  = new Thread(openingPage);
				t.start();
				frame.setGame(game);


				// and the game starts ...
			}
		});
    }
}
