package game.objects;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Titouan Vervack
 */
public class OpeningAction extends AbstractAction {

    private MainModel model;
    
    public OpeningAction(MainModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getID() == 0) {
            model.change();
        } else {
            //Exit on openingscreen
            System.exit(0);
        }
    }
}
