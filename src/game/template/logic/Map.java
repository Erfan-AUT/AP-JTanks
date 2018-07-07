package game.template.logic;

import game.template.logic.cellfillers.Block;
import game.template.logic.cellfillers.ComputerTank;
import game.template.logic.cellfillers.GameObject;
import game.template.logic.cellfillers.UserTank;
import game.template.logic.utils.FileUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {
    /**
     * is the base map for everything that happens in the game.
     */
    private ArrayList<GameObject> allObjects;
    private transient  ArrayList<GameObject> visibleObjects;
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

    /**
     * To load from scratch,
     * each line in the text file represents a row of the actual map:
     * and its format is: Type-x-y.
     * post-b is for bullet-passable
     * post-t is for tank-passable
     * e = empty, d = destroyable block, n = non-destroyable block, u = userTank,
     * c = computerTank.
     * empty blocks don't matter because the only image there is its background
     * (Which is to be loaded later.)
     */
    public Map(int level) {
        cameraZeroY = height;
        String fileName = "\\maps\\defaultMaps\\map" + level + ".txt";
        ArrayList<MapData> readObjects = modifyReadString(fileName);
        for (MapData data : readObjects) {
            int y = data.y, x = data.x;
            switch (data.type) {
                case "dt":
                    allObjects.add(new Block(y, x, true, 10, this, 0));
                    break;
                case "n":
                    allObjects.add(new Block(y, x, false, 10, this, 2));
                    break;
                case "db":
                    allObjects.add(new Block(y, x, true, 10, this, 1));
                    break;
                case "nb":
                    allObjects.add(new Block(y, x, false, 10, this, 1));
                    break;
                case "u":
                    mainTank = new UserTank(y, x, 100, this);
                    allObjects.add(mainTank);
                    break;
                case "c":
                    allObjects.add(new ComputerTank(y, x, 100, this, false));
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
            int y = object.getState().locY;
            int x = object.getState().locX;
            if ((y >= cameraZeroY) && (y <= cameraZeroY + cameraHeight))
                if ((x >= cameraZeroX) && (y <= cameraZeroX + cameraWidth))
                    visibleObjects.add(object);
        }
    }

    public boolean doesntGoOutOfMap(GameObject one, boolean trueForVisibleFalseForAll)
    {
        int y = one.getState().locY;
        int x = one.getState().locX;
        int height1 = 0;
        int width1 = 0;
        // int height1 = Math.abs(one.getHeight() * Math.sin(one.getAnimation().getAngle()) / 2);
        //      int width1 = Math.abs(one.getWidth() * Math.cos(one.getAnimation().getAngle()) / 2);
        if (!trueForVisibleFalseForAll) {
            if ((y - height1 >= 0) && (y + height1 <= height) && ((x - width1 >= 0) && (x + width1 <= width)))
                return true;
        }
        else
        {
            if ((y - height1 >= cameraZeroY) && (y + height1 <= cameraZeroY + cameraHeight) &&
                    ((x - width1 >= cameraZeroX) && (x + width1 <= cameraZeroX + cameraWidth)))
                return true;
        }
        return false;
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
