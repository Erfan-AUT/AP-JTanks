package game.template.logic.objectstates;


import game.template.logic.Map;

/**
 * The basic state of every visible game object.
 * containing its location and diameter. (not set by default.)
 */
public class ObjectState {
    public int locX, locY;
    public Map whichMap;

    public ObjectState(int locY, int locX, Map whichMap) {
        this.locX = locX;
        this.locY = locY;
        this.whichMap = whichMap;
        //diam = 32;
    }



    public void update() {}
}
