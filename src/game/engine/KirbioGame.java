package game.engine;

import game.objects.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Titouan Vervack
 */
public class KirbioGame extends Game {

    private World world;
    private GamePanel panel = new GamePanel(this);
    private int dir;
    private static final int STDINCR = 8;
    private int incr = STDINCR;
    private MainModel model;
    private static final String STARTTEXT = "Press \"Enter\" to resume the game.";
    private static final String STOPTEXT = "Game Over";
    private static final String WONTEXT = "You completed the level!";
    private int jump = 0;
    private Kirbio kir;
    private int tempKey;
    private boolean cont = false;
    private int previousKey;
    //1 or 2
    private int doubleSpeed = 1;
    private int doubleCount = 0;
    private long startTime;
    private long endTime;
    private boolean stopLeft = false;
    private boolean stopRight = false;
    private TList openingScreen;
    private boolean decrease = true;

    public KirbioGame() {
        model = new MainModel();
        world = new World(model);
        world.setModel(model);
        world.addKirbio(30, 470);
        world.addBlock(0, 500, getWidth(), 200, true);
        dir = KeyEvent.VK_RIGHT;
        kir = world.getKirbio();
        ArrayList<String> CHOICES = new ArrayList<>();
        CHOICES.add("Start Game");
        CHOICES.add("Exit");
        panel.removeKeyListener(panel.getKeyListeners()[0]);
        openingScreen = new TList(CHOICES, new OpeningAction(model), new Dimension(getWidth(), getHeight()), panel);
        model.addListener(openingScreen);
        GameStarter.start(this, panel);
    }

    public static void main(String[] args) {
        KirbioGame game = new KirbioGame();
    }

    public void prepareLevel(File level) {
        world.removeBlock(world.getTerrain().get(0));
        LevelReader levelReader = new LevelReader(world, level);
        kir.setX(getWidth() / 2);
        kir.setY(getHeight() / 2);
        model.reset();
        //Kirbio standing still
        dir = 1;
    }

    public void reset() {
        doubleSpeed = 1;
        doubleCount = 0;
        cont = false;
        jump = 0;
        previousKey = 0;
        tempKey = 0;
        incr = STDINCR;
        world.reset();
        resetFalling();
        model.reset();
        dir = KeyEvent.VK_RIGHT;
        world.addKirbio(30, 470);
        world.addBlock(0, 500, getWidth(), 200, true);
        dir = KeyEvent.VK_RIGHT;
        kir = world.getKirbio();
        panel.removeKeyListener(panel.getKeyListeners()[0]);
        openingScreen.reset();
        resetGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        dir = e.getKeyCode();
        if (previousKey == dir && doubleCount == 1 && (dir == KeyEvent.VK_LEFT || dir == KeyEvent.VK_RIGHT)) {
            incr = STDINCR * 2;
            endTime = System.currentTimeMillis();
            if (endTime - startTime < 200) {
                doubleSpeed = 2;
                System.out.println("Double!");
            } else {
                doubleSpeed = 1;
            }
        }
        if (dir == KeyEvent.VK_LEFT || dir == KeyEvent.VK_RIGHT) {
            cont = true;
            tempKey = dir;
        }
        if (dir == KeyEvent.VK_ENTER) {
            if (!hasWon()) {
                togglePaused();
            } else {
                reset();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
            cont = false;
            doubleCount++;
            startTime = System.currentTimeMillis();
        }
        previousKey = dir;
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_SPACE) {
            if (jump > 0) {
                //Continue jump animation
                model.setDirection(3);
            } else {
                //Standing still animation
                model.setDirection(0);
            }
            //Stop movement
            if (!cont) {
                dir = 1;
            } else {
                dir = tempKey;
            }
        } else {
            doubleCount = 0;
        }
    }

    @Override
    public void update() {
        if (kir != null) {
            //Controlled stop
            if (world.getFlag() != null) {
                if (kir.getX() + (kir.getWidth() / 2) >= world.getFlag().getX()) {
                    setWon(true);
                }
            }
            if (dir != 1) {
                switch (dir) {
                    case (KeyEvent.VK_RIGHT):
                        if (!stopRight) {
                            moveWorldX(true);
                        }
                        model.setDirection(1);
                        model.incrementR();
                        model.setRight(true);
                        break;
                    case (KeyEvent.VK_LEFT):
                        if (!stopLeft) {
                            moveWorldX(false);
                        }
                        model.setDirection(1);
                        model.incrementL();
                        model.setRight(false);
                        break;
                    case (KeyEvent.VK_DOWN):
                        //Bukken
                        break;
                    case (KeyEvent.VK_SPACE):
                        //Cannot jump in midair
                        if (jump == 0 && world.getFalling().get(kir).contains(false)) {
                            jump = 12;
                            incr = STDINCR * doubleSpeed;
                            model.setDirection(3);
                        }
                        break;
                }
                //Stop movement
                if (!cont) {
                    dir = 0;
                }
            }
            if (jump > 6) {
                //Move while jumping up
                if (cont) {
                    dir = tempKey;
                }
                if (decrease) {
                    kir.incrementY(-incr);
                }
                jump--;
            } else if (jump > 0) {
                if (decrease) {
                    kir.incrementY(incr);
                }
                jump--;
                if (jump == 0) {
                    incr = STDINCR;
                    if (!cont) {
                        model.setDirection(0);
                    } else {
                        incr = STDINCR * doubleSpeed;
                    }
                }
            }
            if (doubleCount >= 2) {
                doubleCount = 0;
                incr = STDINCR;
            }

            resetFalling();
            Stack<Creature> creatures = (Stack<Creature>) world.getCreatures().clone();
            while (!creatures.isEmpty()) {
                Creature creature = creatures.pop();
                Stack<Terrain> terrains = (Stack<Terrain>) world.getTerrain().clone();
                while (!terrains.isEmpty()) {
                    Terrain terrain = terrains.pop();
                    ArrayList<Integer> collisions = creature.collide(terrain);
                    Stack<Boolean> falling = world.getFalling().get(creature);
                    if (collisions.contains(0)) {
                        falling.push(true);
                        world.getFalling().put(creature, falling);
                    } else if (collisions.contains(1) && collisions.contains(2)) {
                        falling.push(false);
                        world.getFalling().put(creature, falling);
                    } else if (collisions.contains(1) && collisions.contains(4)) {
                        //right
                        stopRight = true;
                    } else if (collisions.contains(2) && collisions.contains(3)) {
                        //left
                        stopLeft = true;
                    } else if (collisions.contains(2) && (creature.getBottomRight().y - terrain.getUpperLeft().y < creature.getHeight()) && (creature.getUpperLeft().x >= terrain.getBottomRight().x - incr)) {
                        //Half left
                        stopLeft = true;
                    } else if (collisions.contains(1) && (creature.getBottomRight().y - terrain.getUpperLeft().y < creature.getHeight()) && (creature.getBottomRight().x <= terrain.getUpperLeft().x + incr)) {
                        //Half right
                        stopRight = true;
                    } else {
                        falling.push(false);
                        world.getFalling().put(creature, falling);
                    }
                    if (creature.getY() > getHeight()) {
                        world.removeCreature(creature);
                        System.err.println(creature.toString() + " has fallen to his death.");
                        stopGame();
                        break;
                    }
                }
            }

            //Gravity
            decrease = true;
            Stack<Creature> creatures2 = (Stack<Creature>) world.getCreatures().clone();
            while (!creatures2.isEmpty()) {
                Creature creature = creatures2.pop();
                if (!world.getFalling().get(creature).contains(false)) {
                    if (jump == 0) {
                        creature.incrementY(incr*2);
                    }
                } else {
                    if (jump != 0) {
                        decrease = false;
                    }
                }
            }
        }
    }

    public void moveWorldX(boolean right) {
        Stack<Terrain> terrains = (Stack<Terrain>) world.getTerrain().clone();
        while (!terrains.isEmpty()) {
            Terrain terrain = terrains.pop();
            if (right) {
                terrain.incrementX(-incr);
            } else {
                terrain.incrementX(incr);
            }
        }
    }

    public void moveWorldY(boolean up) {
        Stack<Terrain> terrains = (Stack<Terrain>) world.getTerrain().clone();
        while (!terrains.isEmpty()) {
            Terrain terrain = terrains.pop();
            if (up) {
                terrain.incrementY(incr);
            } else {
                terrain.incrementY(-incr);

            }
        }
    }

    public void resetFalling() {
        world.getFalling().put(kir, new Stack<Boolean>());
        stopLeft = false;
        stopRight = false;
    }

    @Override
    public void draw(Graphics2D g) {
        //Side GUI = 16 pixels
        //Top/Bottom GUI = 38 pixels
        if (isStarting()) {
            openingScreen.draw(g);
            for (World e : world.getObjects()) {
                e.draw(g);
            }
            model.setDirection(1);
            if (kir.getX() >= (1000 - kir.getWidth())) {
                dir = KeyEvent.VK_LEFT;
            } else if (kir.getX() <= (0 + kir.getWidth())) {
                dir = KeyEvent.VK_RIGHT;
            }
            switch (dir) {
                case (KeyEvent.VK_RIGHT):
                    kir.incrementX(incr);
                    model.incrementR();
                    model.setRight(true);
                    break;
                case (KeyEvent.VK_LEFT):
                    kir.incrementX(-incr);
                    model.incrementL();
                    model.setRight(false);
                    break;
            }
        } else if (hasWon()) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.setColor(Color.BLACK);
            g.setFont(new Font(g.getFont().getFontName(), 0, 30));
            g.drawString(WONTEXT, getWidth() / 2 - 150, getHeight() / 2);
        } else if (isOver()) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.setColor(Color.BLACK);
            g.setFont(new Font(g.getFont().getFontName(), 0, 30));
            g.drawString(STOPTEXT, getWidth() / 2 - 75, getHeight() / 2);
        } else if (!isPaused()) {
            for (World e : world.getObjects()) {
                e.draw(g);
            }
        } else if (isPaused()) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.setColor(Color.BLACK);
            g.setFont(new Font(g.getFont().getFontName(), 0, 30));
            g.drawString(STARTTEXT, getWidth() / 2 - 200, getHeight() / 2);
            for (World e : world.getObjects()) {
                e.draw(g);
            }
        }
    }
}