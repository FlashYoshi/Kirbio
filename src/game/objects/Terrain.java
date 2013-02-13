package game.objects;

import java.awt.Graphics2D;

/**
 *
 * @author Titouan Vervack
 */
public class Terrain extends World {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;

    public Terrain(int x, int y, int width, int height, MainModel model, boolean visible) {
        super(model);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    @Override
    public void draw(Graphics2D g) {
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Point getUpperLeft() {
        return new Point(getX(), getY());
    }

    @Override
    public Point getBottomRight() {
        return new Point(getX() + getWidth(), getY() + getHeight());
    }

    public void incrementX(int incr) {
        x += incr;
    }

    public void incrementY(int incr) {
        y += incr;
    }
}
