package game.engine;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Titouan Vervack
 */
public class GameStarter {

    public static void start(final Game game, final GamePanel panel) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame window = new JFrame(game.getTitle());
                window.setSize(game.getWidth(), game.getHeight());
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.add(panel);
                window.setVisible(true);
                Thread thread = new Thread(new GameInternals(game, panel));
                thread.start();
            }
        });
    }
}
