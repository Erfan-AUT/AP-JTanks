package game.template.logic;

import game.template.bufferstrategy.OpeningPage;
import game.template.logic.Map;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.UserTank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkUser extends User implements Runnable {
    private boolean isFirstTime = true;

    public NetworkUser(Map map, boolean trueForServerFalseForClient) {
        this.trueForServerFalseForClient = trueForServerFalseForClient;
        if (trueForServerFalseForClient)
            this.map = map;
    }


    @Override
    public void run() {
        if (trueForServerFalseForClient) {
            try (ServerSocket server = new ServerSocket(7654)) {
                Socket client = server.accept();
                byte[] maxSized = new byte[2048];
                try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
                    if (isFirstTime) {
                        out.writeObject(OpeningPage.mapName);
                    } else {
                        out.writeObject(map.getVolatileObjects());
                    }
                }

                try (ObjectInputStream tempIn = new ObjectInputStream(client.getInputStream())) {
                    try {
                        //Thread.sleep until the game state has refreshed?
                        if (!isFirstTime) {
                            NetworkData newData = (NetworkData) tempIn.readObject();
                            updateFromNetwork(newData);
                        } else
                            isFirstTime = false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            try (Socket server = new Socket("127.0.0.1", 7654)) {
                try (ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream())) {
                    if (!isFirstTime) {
                        NetworkData data = new NetworkData(isKeyUP(), isKeyDOWN(), isKeyRIGHT(),
                                isKeyLEFT(), isMouseRightClickPressed(),
                                isMouseLeftClickPressed(), isMouseMoved(), getMouseX(), getMouseY());
                        out.writeObject(data);
                        out.flush();
                    }
                    try (ObjectInputStream tempIn = new ObjectInputStream(server.getInputStream())) {
                        try {
                            if (!isFirstTime)
                                map.setVolatileObjects((ArrayList<GameObject>) tempIn.readObject());
                            else {
                                map = new Map((String) tempIn.readObject(), number);
                                isFirstTime = false;
                            }
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


    private byte[] createVolatileBytes() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream tempOut = new ObjectOutputStream(bos)) {
            tempOut.writeObject(map.getVolatileObjects());
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
