/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.template.logic.Map;
import game.template.logic.NetworkUser;
import game.template.logic.User;
import game.template.logic.utils.Music;

import java.io.File;
import java.io.Serializable;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 * <p>
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 * http://gameprogrammingpatterns.com/game-loop.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable, Serializable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private GameFrame canvas;
    private GameState state = new GameState();
    private User user;
    private Map map;
    private boolean isOnNetwork = false;
    private boolean trueForServerFalseForClient = true;
    private boolean gameOver = false;
    private Music music;

    public GameLoop(GameFrame frame) {
        canvas = frame;
        //this.user = user;
        //   map = new Map(1, state);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init(Map map) {
        // Perform all initializations ...
        // state = new GameState();
        //For now false.
        music = new Music("." + File.separator + "sick.mp3", true);
        ThreadPool.execute(music);

//        if (isOnNetwork)
//            user = new NetworkUser(map, trueForServerFalseForClient);
//        else
//            user = new User(map, true);
        this.map = map;
        map.getMainTank().setUser(user);
        state.setMap(map);
        canvas.addKeyListener(user.getKeyHandler());
        canvas.addMouseListener(user.getMouseHandler());
        canvas.addMouseMotionListener(user.getMouseHandler());
        //This might need to change.

        //user = new NetworkUser(map, true);
    }

    @Override
    public void run() {
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //
                state.update();
                canvas.render(state);
                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
        music.close();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameState getState() {
        return state;
    }
}
