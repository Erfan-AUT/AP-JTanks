package game.template.logic.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music implements Runnable {
    private String loc;

    public Music(String loc) {
        this.loc = loc;
    }

    @Override
    public void run() {
        try {
            Player player = new Player(new FileInputStream(loc));
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
