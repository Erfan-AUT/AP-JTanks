package game.template.logic;

import game.template.bufferstrategy.GameFrame;
import game.template.bufferstrategy.GameState;
import game.template.logic.cellfillers.*;
import game.template.logic.utils.FileUtils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Map implements Serializable {
    /**
     * is the base map for everything that happens in the game.
     */
    private ArrayList<GameObject> allObjects = new ArrayList<>();
    private transient ArrayList<GameObject> visibleObjects = new ArrayList<>();
    //Purely arbitrary.
    //Assuming normal cartesian coordinates.
    //Starts from bottom-left.
    private int height = 4700;
    private int width = 3600;
    private UserTank mainTank;
    private ArrayList<UserTank> mainTanks = new ArrayList<>();
    private int cameraWidth = GameFrame.GAME_WIDTH;
    private int cameraHeight = GameFrame.GAME_HEIGHT;
    //These two should change with movement.
    private int[] cameraZeroXs = {0, 0};
    private int[] cameraZeroYs = new int[2];
    private boolean isOnNetwork;
    public static ArrayList<Bullet> bullets = new ArrayList<>(0);

    /**
     * To load from scratch,
     * each line in the text file represents a row of the actual map:
     * and its format is: Type-x-y.
     * post-b is for bullet-passable
     * post-t is for tank-passable
     * post-main is for image type = 1, 2, 3..
     * e = empty, d = destroyable block, p = plant, u = userTank,
     * c = computerTank, w = wicket, cf = cannonFood, t = teazel, w = wicket
     * empty blocks don't matter because the only image there is its background
     * (Which is to be loaded later.)
     */
    public Map(int level, boolean isOnNetwork) {
        // cameraZeroY = height;
       // bullets = new ArrayList<>();
        String fileName = ".\\maps\\defaultMaps\\map" + level + ".txt";
        ArrayList<MapData> readObjects = modifyReadString(fileName);
        String softWall = ".\\images\\softWall";
        String eTank = ".\\Move\\ETank";
        String wicket = ".\\images\\wicket";
        String teazel = ".\\images\\teazel";
        this.isOnNetwork = isOnNetwork;
        for (MapData data : readObjects) {
            int y = data.y, x = data.x;
           // System.out.println(data.type);
            //  Soon to be reloaded.
            switch (data.type) {
                case "cf":
                    allObjects.add(new Block(y, x, false, 40, this, 0, ".\\images\\CannonFood.png", true, data.type));
                    break;
                case "p":
                    allObjects.add(new Block(y, x, false, 0, this, 0, ".\\images\\plant.png", false, data.type));
                    break;
                case "t1":
                    allObjects.add(new Block(y, x, false, 0, this, 0, teazel + "1.png", false, data.type));
                    break;
                case "t2":
                    allObjects.add(new Block(y, x, false, 0, this, 0, teazel + "2.png", false, data.type));
                    break;
                case "nd":
                    allObjects.add(new Block(y, x, false, 0, this, 2, ".\\images\\HardWall.png", false, data.type));
                    break;
                case "d":
                    allObjects.add(new Block(y, x, true, 4, this, 2, softWall + ".png", false, data.type));
                    break;
                case "w1":
                    allObjects.add(new Block(y, x, true, 40, this, 2, wicket + "1.png", false, data.type));
                    break;
                case "w2":
                    allObjects.add(new Block(y, x, true, 40, this, 2, wicket + "2.png", false, data.type));
                    break;
                case "c1":
                    allObjects.add(new ComputerTank(y, x, 100, this, false, eTank));
                    break;
                case "c2":
                    allObjects.add(new ComputerTank(y, x, 200, this, false, eTank + "2"));
                    break;
                case "c3":
                    allObjects.add(new ComputerTank(y, x, 300, this, false, eTank + "3"));
                    break;
                case "r":
                    allObjects.add(new ComputerTank(y, x, 50, this, true, ".\\Move\\Robot"));
                    break;
                case "u":
                    // UserTank userTank = new UserTank(y, x, 100, this, )
                    UserTank userTank = new UserTank(y, x, 100, this, ".\\Move\\Tank");
                    mainTanks.add(userTank);
                    allObjects.add(userTank);
                    break;
            }
        }
//        for (GameObject object: allObjects)
//            object.readContents();
        mainTank = mainTanks.get(0);
    }

    //To Load from savedData, doing this in this way because we could add loading from multiple savedDatas later.
    public Map(String fileName) {
        Map orig = FileUtils.readMap(fileName);
        allObjects = orig.allObjects;
        visibleObjects = orig.visibleObjects;
        height = orig.height;
        width = orig.width;
        orig = null;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<GameObject> getAllObjects() {
        return allObjects;
    }

    public ArrayList<GameObject> getVisibleObjects() {
        return visibleObjects;
    }


    private ArrayList<MapData> modifyReadString(String fileName) {
        ArrayList<String> read = FileUtils.readWithStream(fileName);
        ArrayList<MapData> returnValue = new ArrayList<>();
        for (String s : read) {
            StringBuilder value = new StringBuilder();
            for (char c : s.toCharArray()) {
                if ((c != ' ') && (c != '\n'))
                    value.append(c);
                else {
                    returnValue.add(new MapData(value.toString()));
                    value.delete(0, value.length());
                }
            }
            returnValue.add(new MapData(value.toString()));
            value.delete(0, value.length());
        }
        return returnValue;
    }

    public void updateWidth(int user) {
//        int maxX = 0;
//        for (GameObject object : visibleObjects) {
//            if (object.locX + object.getWidth() > maxX)
//                maxX = object.locX + object.getWidth();
//        }
//        if (width > maxX)
//            width = maxX;
        int maxX = 0;
        for (GameObject object : allObjects) {
            if (object != mainTanks.get(user)) {
                int felan = object.locY - (object.getHeight() / 2);
                if ((felan <= mainTanks.get(user).locY) && (felan >= mainTanks.get(user).locY - mainTanks.get(user).getHeight()))
                    if (object.locX > maxX)
                        maxX = object.locX + object.getWidth();
            }
        }
        width = maxX;
    }


    public void updateCameraZeros(int user) {
//        if (mainTank.isMoving()) {
//            int i = 1;
//        }
        if (mainTanks.get(user).locX + cameraWidth <= width) {
            if (mainTanks.get(user).locX > 300)
                cameraZeroXs[user] = mainTanks.get(user).locX - 300;
            else
                cameraZeroXs[user] = 0;
        } else
            cameraZeroXs[user] = width - cameraWidth;
        //int animHeight = GameState.getRelativeHeightWidth(mainTank).height;
        if (mainTanks.get(user).locY + cameraHeight <= height) {
            if (mainTanks.get(user).locY > 300)
                cameraZeroYs[user] = mainTanks.get(user).locY - 300;
            else
                cameraZeroYs[user] = 0;
        } else
            cameraZeroYs[user] = height - cameraHeight;
//        System.out.println("Camera x is:" + cameraZeroX);
//        System.out.println("Camera y is:" + cameraZeroY);
    }


    public void updateVisibleObjects(int user) {
        //Resets everything in order to see what has been renewed.
        visibleObjects.clear();
        for (GameObject object : allObjects) {
            int y = object.locY;
            int x = object.locX;
            if ((y >= cameraZeroYs[user] - object.getHeight()) && (y <= cameraZeroYs[user] + cameraHeight))
                if ((x >= cameraZeroXs[user] - object.getWidth()) && (x <= cameraZeroXs[user] + cameraWidth))
                    visibleObjects.add(object);
        }
        //
    }

    public boolean doesntGoOutOfMap(GameObject one, boolean trueForVisibleFalseForAll, int user) {
        Dimension d = GameState.getRelativeHeightWidth(one);
        int y = one.locY;
        int x = one.locX;
        int height1 = d.height;
        int width1 = d.width;

        if (!trueForVisibleFalseForAll) {
            if ((y + height1 <= height) && (y >= 20) && ((x >= 0) && (x + width1 <= width)))
                return true;
        } else {
            if ((y - height1 >= cameraZeroYs[user]) && (y <= cameraZeroYs[user] + cameraHeight) &&
                    ((x >= cameraZeroXs[user]) && (x + width1 <= cameraZeroXs[user] + cameraWidth)))
                return true;
        }
        System.out.println("Goes out of map.");
        System.out.println("y:" + y);
        System.out.println("x:" + x);
        return false;
    }


    public synchronized void update(int user) {
        if (!isOnNetwork) {
            updateCameraZeros(user);
            updateVisibleObjects(user);
            updateWidth(user);
            for (Iterator it = allObjects.iterator(); it.hasNext();) {
//                if (object instanceof Bullet)
//                {
//                    int i = 12;
//                }
                try {
                    ((GameObject)(it.next())).update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public UserTank getMainTank() {
        return mainTank;
    }

    private class MapData {
        public int y;
        public int x;
        public String type;

        public MapData(String value) {
            String[] values = value.split("-");
            this.y = Integer.parseInt(values[1]);
            this.x = Integer.parseInt(values[2]);
            this.type = values[0];
        }
    }

    public int getCameraZeroX(int user) {
        return cameraZeroXs[user];
    }

    public int getCameraZeroY(int user) {
        return cameraZeroYs[user];
    }

    public ArrayList<UserTank> getMainTanks() {
        return mainTanks;
    }

    public boolean isOnNetwork() {
        return isOnNetwork;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

  //  public static
}
