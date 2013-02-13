package game.objects;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Titouan Vervack
 */
public class MainModel extends TModel {

    private int direction = 0;
    private int rightCounter = 0;
    private int leftCounter = 0;
    private boolean right = true;
    private ArrayList<ImageIcon> movementRight = new ArrayList<>();
    private ArrayList<ImageIcon> movementLeft = new ArrayList<>();

    public MainModel() {
        movementRight.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioStandingStillRightSmall.png")));
        movementRight.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioRunningRight1Small.png")));
        movementRight.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioRunningRight2Small.png")));
        movementRight.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioJumpingRightSmall.png")));
        movementLeft.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioStandingStillLeftSmall.png")));
        movementLeft.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioRunningLeft1Small.png")));
        movementLeft.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioRunningLeft2Small.png")));
        movementLeft.add(new ImageIcon(Kirbio.class.getResource("../images/KirbioJumpingLeftSmall.png")));
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Image getKirbioImage() {
        ArrayList<ImageIcon> movement = movementRight;
        int counter = rightCounter;
        if (!right) {
            movement = movementLeft;
            counter = leftCounter;
        }
        if (direction == 1) {
            return movement.get(direction + counter).getImage();
        } else {
            return movement.get(direction).getImage();
        }
    }

    public void incrementR() {
        rightCounter = (rightCounter + 1) % 2;
    }

    public void incrementL() {
        leftCounter = (leftCounter + 1) % 2;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    
    public void reset(){
        direction = 0;
        rightCounter = 0;
        leftCounter = 0;
        right = true;
    }
}
