package game.objects;

import java.awt.Graphics2D;

/**
 *
 * @author Titouan Vervack
 */
public class Kirbio extends Creature {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    public Kirbio(int x, int y, MainModel model) {
        super(x, y, model);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(model.getKirbioImage(), x - (WIDTH / 2), y - (WIDTH / 2), WIDTH, HEIGHT, null);
    }
    
    @Override
    public int getWidth(){
        return WIDTH;
    }
    
    @Override
    public int getHeight(){
        return HEIGHT;
    }
    
    @Override
    public String toString(){
        return "Kirbio";
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
}
