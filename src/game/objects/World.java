package game.objects;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Titouan Vervack
 */
public class World {

    private Stack<Terrain> terrainList = new Stack<>();
    private Stack<Creature> creaturesList = new Stack<>();
    private Stack<World> objectsList = new Stack<>();
    private HashMap<Creature, Stack<Boolean>> falling = new HashMap<>();
    protected MainModel model = new MainModel();
    private Kirbio kirbio;  
    private Flag flag;
    
    public World(MainModel model){
        this.model = model;
    }
    
    public void draw(Graphics2D g) {
    }
        
    public void addBlock(int x, int y, int width, int height, boolean visible) {
        Terrain terrain = new Block(x, y, width, height, model, visible);
        terrainList.push(terrain);
        objectsList.push(terrain);
    }

    public void removeBlock(Terrain terrain) {
        terrainList.remove(terrain);
        objectsList.remove(terrain);
    }
    
    public void addFlag(int x, int y, boolean visible){
        flag = new Flag(x, y, model, visible);
        terrainList.push(flag);
        objectsList.push(flag);
    }

    public void addKirbio(int x, int y) {
        kirbio = new Kirbio(x, y, model);
        falling.put(kirbio, new Stack<Boolean>());
        creaturesList.push(kirbio);
        objectsList.push(kirbio);
    }

    public void addBadio(int x, int y) {
        Creature creature = new Badio(x, y, model);
        falling.put(creature, new Stack<Boolean>());
        creaturesList.push(creature);
        objectsList.push(creature);
    }

    public void removeCreature(Creature creature) {
        falling.remove(creature);
        creaturesList.remove(creature);
        objectsList.remove(creature);
    }

    public Stack<Terrain> getTerrain() {
        return terrainList;
    }

    public Stack<Creature> getCreatures() {
        return creaturesList;
    }

    public Stack<World> getObjects() {
        return objectsList;
    }
    
    public HashMap<Creature, Stack<Boolean>> getFalling(){
        return falling;
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
    
    public void setModel(MainModel model){
        this.model = model;
    }
    
    public Kirbio getKirbio(){
        return kirbio;
    }
    
    public Flag getFlag(){
        return flag;
    }
    
    public Point getBottomRight(){
        return null;
    }
    
    public Point getUpperLeft(){
        return null;
    }
    
    public void reset(){
        kirbio = null;
        flag = null;
        terrainList.clear();
        objectsList.clear();
        creaturesList.clear();
        falling.clear();
    }
}
