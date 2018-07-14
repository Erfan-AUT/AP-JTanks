package game.template.logic.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music implements Runnable {
    private String loc;
    private boolean onRepeat;
    private Player player;

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

    public Music(String loc, boolean onRepeat) {
        this(loc);
        this.onRepeat = onRepeat;
    }


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

    public void close() {
        player.close();
    }
}
