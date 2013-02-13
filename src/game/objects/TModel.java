package game.objects;

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Titouan Vervack
 */
public class TModel {

    public ArrayList<ChangeListener> listeners = new ArrayList<>();

    public TModel() {
    }

    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(ChangeListener listener){
        listeners.remove(listener);
    }
    
    public void change(){
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener l : listeners){
            l.stateChanged(event);
        }
    }
}
