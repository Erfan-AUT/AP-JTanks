package game.template.logic;

public class Map {
    /**
     * is the base map for everything that happens in the game.
     */
    private Cell[][] cells;
    private int height;
    private int width;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        cells = new Cell[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
