package game.objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Titouan Vervack
 */
public abstract class Creature extends World {

    protected int x;
    protected int y;

    public Creature(int x, int y, MainModel model) {
        super(model);
        this.x = x;
        this.y = y;
    }

    @Override
    public abstract void draw(Graphics2D g);

    public void incrementX(int incr) {
        x += incr;
    }

    public void incrementY(int incr) {
        y += incr;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public abstract int getWidth();

    @Override
    public abstract int getHeight();

    @Override
    public Point getUpperLeft() {
        return new Point(getX() - getWidth() / 2, getY() - getHeight() / 2);
    }
    
    @Override
    public Point getBottomRight(){
        return new Point(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public ArrayList<Integer> collide(World obj) {
        Point leftObj = obj.getUpperLeft();
        Point rightObj = obj.getBottomRight();
        Point leftThis = getUpperLeft();
        Point rightThis = getBottomRight();

        boolean rechtsonder = rightThis.x + 1 > leftObj.x && rightThis.x + 1 < rightObj.x && rightThis.y + 1 > leftObj.y && rightThis.y + 1 < rightObj.y;
        boolean linksonder = leftThis.x + 1 > leftObj.x && leftThis.x + 1 < rightObj.x && rightThis.y + 1 > leftObj.y && rightThis.y + 1 < rightObj.y;
        boolean linksboven = leftThis.x + 1 > leftObj.x && leftThis.x + 1 < rightObj.x && leftThis.y + 1 > leftObj.y && leftThis.y + 1 < rightObj.y;
        boolean rechtsboven = rightThis.x + 1 > leftObj.x && rightThis.x + 1 < rightObj.x && leftThis.y + 1 > leftObj.y && leftThis.y + 1 < rightObj.y;

        ArrayList<Integer> collisions = new ArrayList<>();

        if (rechtsonder) {
            collisions.add(1);
        }
        if (linksonder) {
            collisions.add(2);
        }
        if (linksboven) {
            collisions.add(3);
        }
        if (rechtsboven) {
            collisions.add(4);
        }
        if (collisions.isEmpty()) {
            collisions.add(0);
        }

        return collisions;
    }
}