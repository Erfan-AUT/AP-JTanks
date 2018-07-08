package game.template.logic;

import game.template.bufferstrategy.GameState;
import game.template.logic.cellfillers.Block;
import game.template.logic.cellfillers.ComputerTank;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.UserTank;
import game.template.logic.utils.FileUtils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {
    /**
     * is the base map for everything that happens in the game.
     */
    private ArrayList<GameObject> allObjects = new ArrayList<>();
    private transient  ArrayList<GameObject> visibleObjects = new ArrayList<>();
    //Purely arbitrary.
    //Assuming normal cartesian coordinates.
    //Starts from bottom-left.
    private int height = 500;
    private int width = 500;
    private UserTank mainTank;
    private int cameraWidth = 100;
    private int cameraHeight = 100;
    //These two should change with movement.
    private int cameraZeroX = 0;
    private int cameraZeroY;
    private GameState state;

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
    public Map(int level, GameState state) {
       // cameraZeroY = height;
        String fileName = ".\\maps\\defaultMaps\\map" + level + ".txt";
        ArrayList<MapData> readObjects = modifyReadString(fileName);
        String softWall = ".\\images\\softWall";
        String eTank = ".\\Move\\Etank";
        String wicket = ".\\images\\wicket";
        String teazel = ".\\images\\teazel";
        for (MapData data : readObjects) {
            int y = data.y, x = data.x;
            System.out.println(data.type);
          //  Soon to be reloaded.
            switch (data.type) {
                case "p":
                    allObjects.add(new Block(y, x, false, 10, this, 0, ".\\images\\plant.png", false));
                    break;
                case "t1":
                    allObjects.add(new Block(y, x, false, 10, this, 0, teazel + "1.png", false));
                    break;
                case "t2":
                    allObjects.add(new Block(y, x, false, 10, this, 0, teazel + "2.png", false));
                    break;
                case "nd2":
                    allObjects.add(new Block(y, x, true, 40, this, 2, ".\\images\\", true));
                    break;
                case "d0":
                    allObjects.add(new Block(y, x, true, 10, this, 2, softWall + ".png", false));
                    break;
                case "d1":
                    allObjects.add(new Block(y, x, true, 20, this, 2, softWall + "1.png", false));
                    break;
                case "d2":
                    allObjects.add(new Block(y, x, true, 30, this, 2, softWall + "2.png", false));
                    break;
                case "d3":
                    allObjects.add(new Block(y, x, true, 40, this, 2, softWall + "3.png", false));
                    break;
                case "w1":
                    allObjects.add(new Block(y, x, true, 40, this, 2, wicket + "1.png", true));
                    break;
                case "w2":
                    allObjects.add(new Block(y, x, true, 40, this, 2, wicket + "2.png", true));
                    break;
                case "c1":
                    allObjects.add(new ComputerTank(y, x, 100, this, false, eTank + "1.png"));
                    break;
                case "c2":
                    allObjects.add(new ComputerTank(y, x, 200, this, false, eTank + "2.png"));
                    break;
                case "c3":
                    allObjects.add(new ComputerTank(y, x, 300, this, false, eTank + "3.png"));
                    break;
                case "r":
                    allObjects.add(new ComputerTank(y, x, 50, this, true, ".\\Move\\Robot"));
                    break;
                case "u":
                    mainTank = new UserTank(y, x, 100, this, ".\\Move\\Tank", state);
                    allObjects.add(mainTank);
                    break;
            }
        }

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
                if (c != ' ')
                    value.append(c);
                else {
                    returnValue.add(new MapData(value.toString()));
                    value.delete(0, value.length());
                }

            }
        }
        return returnValue;
    }

    public void updateVisibleObjects()
    {
        //Resets everything in order to see what has been renewed.
        visibleObjects.clear();
        for (GameObject object : allObjects)
        {
            int y = object.locY;
            int x = object.locX;
            if ((y >= cameraZeroY) && (y <= cameraZeroY + cameraHeight))
                if ((x >= cameraZeroX) && (y <= cameraZeroX + cameraWidth))
                    visibleObjects.add(object);
        }
    }

    public boolean doesntGoOutOfMap(GameObject one, boolean trueForVisibleFalseForAll)
    {
        Dimension d = GameState.getRelativeHeightWidth(one);
        int y = one.locY;
        int x = one.locX;
        int height1 = d.height;
        int width1 = d.width;

        if (!trueForVisibleFalseForAll) {
            if ((y - height1 >= 0) && (y <= height) && ((x >= 0) && (x + width1 <= width)))
                return true;
        }
        else
        {
            if ((y - height1 >= cameraZeroY) && (y <= cameraZeroY + cameraHeight) &&
                    ((x >= cameraZeroX) && (x + width1 <= cameraZeroX + cameraWidth)))
                return true;
        }
        return false;
    }

    public void updateCameraZeros()
    {
        if (mainTank.getAnimation().x + cameraWidth <= width)
            cameraZeroX = mainTank.getAnimation().x;
        else
            cameraZeroX = width - cameraWidth;
        if (mainTank.getAnimation().y - mainTank.getAnimation().getFrameHeight() + cameraHeight <= height)
            cameraZeroY = mainTank.getAnimation().x - mainTank.getAnimation().getFrameHeight();
        else
            cameraZeroX = height - cameraHeight;
    }

    public void update()
    {
        updateCameraZeros();
        updateVisibleObjects();
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
}
