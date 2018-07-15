package game.template.logic.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music implements Runnable {
    private String loc;
    private boolean onRepeat;
    private Player player;

    /**
     * Creates a new music player giving its address.
     * @param loc the address.
     */
    public Music(String loc) {
        this.loc = loc;
        try {
            player = new Player(new FileInputStream(loc));
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates a music player on repeat.
     * @param loc its location
     * @param onRepeat whether or not it is on repeat.
     */

    public Music(String loc, boolean onRepeat) {
        this(loc);
        this.onRepeat = onRepeat;
    }


    /**
     * plays the music accordingly.
     */
    @Override
    public void run() {
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        while (onRepeat)
        {
            if (player.isComplete())
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
        }

    }

    /**
     * closes the player.
     */

    public void close() {
        player.close();
    }
}
