package game.template.logic.cellfillers;

import game.template.graphics.MasterAnimation;
import game.template.logic.Map;

/**
 * basis of every block that the user sees in the game.
 */
public class Block extends GameObject {

    //Whether or not tanks or bullets can pass through it.
    private boolean isPassableByTank = false;
    private boolean isPassableByBullet = false;
    private int initialHealth;
    private boolean isGift;
    private String type;

    public Block(int y, int x, boolean isDestructible, int health, Map whichMap, int pass,
                 String location, boolean isGift, String type) {
        super(y, x, isDestructible, health, whichMap, location);
        this.type = type;
        initialHealth = health;
        if (isGift) {
            pass = 0;
            isDestructible = true;
        }
        if (pass == 0)
            isDestructible = false;
        //TODO : Note that in collision of gift block and user tank, gift gets added to tank.
        //Jeez.
        switch (pass) {
            case 0:
                isPassableByTank = true;
            case 1:
                isPassableByBullet = true;
                break;
        }
        displayTheAnimations();
        //Any other means non-passable.
    }

    @Override
    public void takeDamage(int damage) {
        if (isDestructible()) {
            super.takeDamage(damage);
            readContents(".\\images\\softWall" + Integer.toString(4 - getHealth()) + ".png");
            displayTheAnimations();
        }
    }

//    public void update() {
//        if (isDestructible())
//            if (initialHealth > getHealth()) ;
////                case "d1":
////        allObjects.add(new Block(y, x, true, 20, this, 2, softWall + "1.png", false));
////        break;
////        case "d2":
////        allObjects.add(new Block(y, x, true, 30, this, 2, softWall + "2.png", false));
////        break;
////        case "d3":
////        allObjects.add(new Block(y, x, true, 40, this, 2, softWall + "3.png", false));
////        break;
//    }


    public boolean isPassableByTank() {
        return isPassableByTank;
    }

    public boolean isPassableByBullet() {
        return isPassableByBullet;
    }

    public boolean isGift() {
        return isGift;
    }

    public String getType() {
        return type;
    }
}
