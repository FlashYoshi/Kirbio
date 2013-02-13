package game.objects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Titouan Vervack
 */
public class Block extends Terrain {

    private static final Color COLOR = Color.RED;

    public Block(int x, int y, int width, int height, MainModel model, boolean visible) {
        super(x, y, width, height, model, visible);
    }

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.setColor(COLOR);
            g.fillRect(x, y, width, height);
        }
    }
}
