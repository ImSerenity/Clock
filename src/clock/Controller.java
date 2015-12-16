package clock;

import java.awt.event.*;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;
    View view;
    
    public Controller(Model m, View v) {
        model = m;
        view = v;
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.update();
                Calendar date2 = Calendar.getInstance();
                Alarm alarm = new Alarm("", 0, 0, 0);
        if(date2.get(Calendar.HOUR) == alarm.getHours() && date2.get(Calendar.MINUTE) == alarm.getMinutes())
        {
            JPanel inputPanel = new JPanel();
            JOptionPane.showConfirmDialog(null, inputPanel, "ALARM: " + alarm.getName(), JOptionPane.OK_CANCEL_OPTION);
        }
            }
        };
        
        timer = new Timer(100, listener);
        timer.start();
    }
}