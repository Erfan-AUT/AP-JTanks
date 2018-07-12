package game.template.logic;

import game.template.logic.Map;
import game.template.logic.cellfillers.UserTank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkUser extends User implements Runnable {


    public NetworkUser(Map map, boolean trueForServerFalseForClient) {
        super(map, trueForServerFalseForClient);
    }

    @Override
    public void run() {
        if (trueForServerFalseForClient) {
            try (ServerSocket server = new ServerSocket(7654)) {
                Socket client = server.accept();
                byte[] maxSized = new byte[2048];
                client.getInputStream().read(maxSized);
                ByteArrayInputStream bis = new ByteArrayInputStream(maxSized);
                try (ObjectInputStream tempIn = new ObjectInputStream(bis)) {
                    try {
                        //Thread.sleep until the game state has refreshed?
                        NetworkData newData = (NetworkData) tempIn.readObject();
                        updateFromNetwork(newData);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                byte[] mapBytes = createMapBytes();
                if (mapBytes != null)
                    client.getOutputStream().write(mapBytes);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try (Socket server = new Socket("127.0.0.1", 7654)) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
                    NetworkData data = new NetworkData(isKeyUP(), isKeyDOWN(), isKeyRIGHT(),
                            isKeyLEFT(), isMouseRightClickPressed(),
                            isMouseLeftClickPressed(), isMouseMoved(), getMouseX(), getMouseY());
                    out.writeObject(data);
                    byte[] maxSized = new byte[4096];
                    ByteArrayInputStream bis = new ByteArrayInputStream(maxSized);
                    try (ObjectInputStream tempIn = new ObjectInputStream(bis)) {
                        try {
                            map = (Map) tempIn.readObject();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private void updateFromNetwork(NetworkData data) {
        setKeyDOWN(data.keyDOWN);
        setKeyUP(data.keyUP);
        setKeyLEFT(data.keyLEFT);
        setKeyRIGHT(data.keyRIGHT);
        setMouseLeftClickPressed(data.mouseLeftClickPressed);
        setMouseRightClickPressed(data.mouseRightClickPressed);
        setMouseMoved(data.mouseMoved);
        setMouseX(data.mouseX);
        setMouseY(data.mouseY);
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

        NetworkData(boolean keyUP, boolean keyDOWN, boolean keyRIGHT, boolean keyLEFT,
                    boolean mouseRightClickPressed, boolean mouseLeftClickPressed,
                    boolean mouseMoved, int mouseX, int mouseY) {
            this.keyUP = keyUP;
            this.keyDOWN = keyDOWN;
            this.keyRIGHT = keyRIGHT;
            this.keyLEFT = keyLEFT;
            this.mouseRightClickPressed = mouseRightClickPressed;
            this.mouseLeftClickPressed = mouseLeftClickPressed;
            this.mouseMoved = mouseMoved;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
    }


}
