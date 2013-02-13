package game.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Titouan Vervack
 */
public class GamePanel extends JPanel{

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        addKeyListener(game);
        addMouseListener(game);
        addMouseMotionListener(game);
        addMouseWheelListener(game);
        setFocusable(true);
        requestFocus();
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg.create();
        game.draw(g);
    }
}
