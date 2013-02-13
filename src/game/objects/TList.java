package game.objects;

import game.engine.GamePanel;
import game.engine.KirbioGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Titouan Vervack
 */
public class TList implements KeyListener, ChangeListener {

    private AbstractAction reaction;
    private ArrayList<String> choices;
    private int selection;
    private int levelSelection;
    private static final int SIZE = 30;
    private Dimension dimension;
    private GamePanel panel;
    private ImageIcon background;
    private ImageIcon lvlSelect;
    private boolean changed = false;
    private int total;
    private File level;

    public TList(ArrayList<String> choises, AbstractAction reaction, Dimension dimension, GamePanel panel) {
        this.choices = choises;
        this.reaction = reaction;
        this.dimension = dimension;
        this.panel = panel;
        panel.addKeyListener(this);
        selection = 0;
        levelSelection = 0;
        //background = new ImageIcon(SpelPanel.class.getResource("../images/openingScreen.png"));
        //lvlSelect = new ImageIcon(SpelPanel.class.getResource("../images/levelSelect.png"));
    }

    public void reset() {
        panel.addKeyListener(this);
        selection = 0;
        levelSelection = 0;
        total = 0;
        changed = false;
    }

    public void draw(Graphics2D g) {
        if (!changed) {
            //g.drawImage(background.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight(), null);
            for (int i = 0; i < choices.size(); i++) {
                if (selection == i) {
                    //g.setColor(new Color(235, 235, 235));
                    g.setColor(Color.CYAN);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.setFont(new Font(g.getFont().getFontName(), 0, SIZE));
                g.drawString(choices.get(i), (int) (dimension.width / 2 - (g.getFontMetrics().getStringBounds(choices.get(i), g).getWidth()) / 2), dimension.height / 2 + (SIZE * i));
            }
        } else {
            //g.drawImage(lvlSelect.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight(), null);
            g.setFont(new Font(g.getFont().getFontName(), 0, SIZE));
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(new File("src/game/levels").toPath())) {
                int i = 0;
                for (Path p : ds) {
                    if (i == levelSelection) {
                        g.setColor(Color.CYAN);
                        level = p.toFile();
                    } else {
                        g.setColor(Color.DARK_GRAY);
                    }
                    g.drawString("" + (i + 1), SIZE * (i + 1), SIZE * 2);
                    i++;
                }
                total = i;
            } catch (IOException e) {
                System.out.println("IO @ TList");
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!changed) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                selection = (selection + 1) % choices.size();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                selection = Math.abs(selection - 1) % choices.size();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                reaction.actionPerformed(new ActionEvent(this, selection, "Selection"));
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                levelSelection = (levelSelection + 1) % total;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                levelSelection = Math.abs(levelSelection - 1) % total;
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                panel.removeKeyListener(this);
                KirbioGame game = (KirbioGame) panel.getGame();
                panel.addKeyListener(game);
                game.prepareLevel(level);
                game.stopStarting();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        changed = true;
    }
}
