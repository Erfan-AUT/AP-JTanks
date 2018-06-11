package game.template.logic.objectstates;


/**
 * The basic state of every visible game object.
 * containing its location and diameter. (not set by default.)
 */
public class ObjectState {
    public int locX, locY;

    public ObjectState(int locY, int locX) {
        this.locX = locX;
        this.locY = locY;
        //diam = 32;
    }

    protected void update() {}
}
