package game.template.logic.utils;

import game.template.logic.Map;
import game.template.logic.cellfillers.UserTank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkUser implements Runnable {
    private Map map;
    private UserTank tank;
    private boolean trueForServerFalseForClient;

    public NetworkUser(Map map, boolean trueForServerFalseForClient) {
        this.trueForServerFalseForClient = trueForServerFalseForClient;
        this.map = map;
        tank = map.getMainTanks().get(1);
    }

    @Override
    public void run() {
        if (trueForServerFalseForClient) {
            try (ServerSocket server = new ServerSocket(7654)) {
                Socket client = server.accept();
                byte[] mapBytes = createMapBytes();
                client.getOutputStream().write(mapBytes);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } else {
            try (Socket server = new Socket("127.0.0.1", 7654)) {
                byte[] maxSized = new byte[2048];
                server.getInputStream().read(maxSized);
                ByteArrayInputStream bis = new ByteArrayInputStream(maxSized);
                try (ObjectInputStream tempIn = new ObjectInputStream(bis)) {
                    try {
                        NetworkData newData = (NetworkData) tempIn.readObject();
                    } catch (Exception ex) {

                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private byte[] createMapBytes() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream tempOut = new ObjectOutputStream(bos)) {
            tempOut.writeObject(map);
            tempOut.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null;
    }


    class NetworkData implements Serializable {
        private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
        private boolean mouseRightClickPressed;
        private boolean mouseLeftClickPressed;
        private boolean mouseMoved;
        private int mouseX, mouseY;
    }


}
