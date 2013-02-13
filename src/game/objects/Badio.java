package game.objects;

import java.awt.Graphics2D;

/**
 *
 * @author Titouan Vervack
 */
public class Badio extends Creature {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    
    public Badio(int x, int y, MainModel model) {
        super(x, y, model);
    }

    @Override
    public void draw(Graphics2D g) {
    }
    
    @Override
    public int getWidth(){
        return WIDTH;
    }
    
    @Override
    public int getHeight(){
        return HEIGHT;
    }
}
