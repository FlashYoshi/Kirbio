package game.engine;

/**
 *
 * @author Titouan Vervack
 */
public class GameInternals implements Runnable {

    private Game game;
    private GamePanel panel;

    public GameInternals(Game game, GamePanel panel) {
        this.game = game;
        this.panel = panel;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        while (!game.isOver()) {
            if (!game.isPaused() && !game.isStarting()) {
                game.update();
            }
            panel.repaint();

            try {
                Thread.sleep(game.getDelay());
            } catch (InterruptedException ex) {
                System.err.println("Exception at repaint(): " + ex.toString());
            }
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            System.err.println("Exception at closing(): " + ex.toString());
        }
        System.exit(0);
    }
}
