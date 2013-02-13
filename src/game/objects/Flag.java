package game.objects;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Titouan Vervack
 */
public class Flag extends Terrain {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 300;
    private ImageIcon flag;

    public Flag(int x, int y, MainModel model, boolean visible) {
        super(x, y, WIDTH, HEIGHT, model, visible);
        flag = new ImageIcon(Kirbio.class.getResource("../images/flag.png"));
    }

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.drawImage(flag.getImage(), x - (WIDTH / 2), y - (WIDTH / 2), WIDTH, HEIGHT, null);
        }
    }
}
