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
    private transient ArrayList<GameObject> visibleObjects;
    private int height;
    private int width;

    /**
     * To load from scratch,
     * each line in the text file represents a row of the actual map:
     * and its format is: Type-x-y.
     * e = empty, d = destroyable block, n = non-destroyable block, u = userTank,
     * c = computerTank.
     */
    public Map(int level) {
        String fileName = "\\maps\\defaultMaps\\map" + level + ".txt";
        ArrayList<MapData> readObjects = modifyReadString(fileName);
        for (MapData data : readObjects)
        {
            int y = data.y, x = data.x;
            switch (data.type)
            {
                case 'd':
                    allObjects.add(new Block(y, x, true, 10, this));
                    break;
                case 'n':
                    allObjects.add(new Block(y, x, false, 10, this));
                    break;
                case 'u':
                    allObjects.add(new UserTank(y, x,100, this ));
                    break;
                case 'c':
                    allObjects.add(new ComputerTank(y, x, 100, this));
                    break;
            }
        }
    }

    //To Load from savedData, doing this in this way because we could add loading from multiple savedDatas later.
    public Map(String fileName)
    {
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

    private ArrayList<MapData> modifyReadString(String fileName)
    {
        ArrayList<String> read = FileUtils.readWithStream(fileName);
        ArrayList<MapData> returnValue = new ArrayList<>();
        for (String s : read)
        {
            StringBuilder value = new StringBuilder("");
            for (char c : s.toCharArray())
                if (c != ' ')
                    value.append(c);
            returnValue.add(new MapData(value.toString()));
        }
        return returnValue;
    }

    private class MapData
    {
        public int y;
        public int x;
        public char type;

        public MapData(String value) {
            String[] values = value.split("-");
            this.y = Integer.parseInt(values[0]);
            this.x = Integer.parseInt(values[1]);
            this.type = values[2].toCharArray()[0];
        }
    }
}
