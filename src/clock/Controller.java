package clock;

import java.awt.event.*;
import javax.swing.Timer;

/**
 * Main Controller class to check for updates and run actions defined in View and Model
 * @author Ian Barnes
 * @author2 Thomas Wood - 09004316
 */
public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;
    View view;
    
    /**
     * The controller runs defined actions for the View and Model
     * @param m
     * @param v 
     */
    public Controller(Model m, View v) {
        model = m;
        view = v;
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.update();
                
                /**
                 * Calls the alarm function from the View class every update
                 */
                view.alarm();
            }
        };
        
        timer = new Timer(100, listener);
        timer.start();
    }
}