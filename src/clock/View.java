package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;
import queuemanager.SortedArrayPriorityQueue;

public class View implements Observer, ActionListener {
    
    ClockPanel panel;
    Alarm alarm;
    queuemanager.SortedArrayPriorityQueue pq = new SortedArrayPriorityQueue<>(100);
    
    public View(Model model) {
        JFrame frame = new JFrame();
        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Start of border layout code
        
        // I've just put a single button in each of the border positions:
        // PAGE_START (i.e. top), PAGE_END (bottom), LINE_START (left) and
        // LINE_END (right). You can omit any of these, or replace the button
        // with something else like a label or a menu bar. Or maybe you can
        // figure out how to pack more than one thing into one of those
        // positions. This is the very simplest border layout possible, just
        // to help you get started.
        
        Container pane = frame.getContentPane();
        
        JButton button = new JButton("Button 1 (PAGE_START)");
        //pane.add(button, BorderLayout.PAGE_START);
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        menuBar = new JMenuBar();
        
        menu = new JMenu("A Menu");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Set an alarm...");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField(5);
                JTextField hours = new JTextField(5);
                JTextField minutes = new JTextField(5);
                JTextField seconds = new JTextField(5);
                JPanel inputPanel = new JPanel();

                inputPanel.add(new JLabel("Name: "));
                inputPanel.add(name);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Hours: "));
                inputPanel.add(hours);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Minutes: "));
                inputPanel.add(minutes);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Seconds: "));
                inputPanel.add(seconds);

                int result =  JOptionPane.showConfirmDialog(null, inputPanel, "Please fill in the details of the alarm below", JOptionPane.OK_CANCEL_OPTION);
                
                String hoursOut = hours.getText();
                int hoursOutInt = Integer.parseInt(hoursOut);
                String minutesOut = minutes.getText();
                int minutesOutInt = Integer.parseInt(minutesOut);
                String secondsOut = seconds.getText();
                int secondsOutInt = Integer.parseInt(secondsOut);
                String nameOut = name.getText();
                
               
                int time = (hoursOutInt * 60 * 60) + (minutesOutInt * 60) + secondsOutInt;
                //alarm.setHours(hoursOutInt);
                //alarm.setMinutes(minutesOutInt);
                //alarm.setSeconds(secondsOutInt);
                alarm = new Alarm();
                try {
                    pq.add(nameOut, time);
                    System.out.println("Adding alarm with Name: " + alarm.getName() + " and Time: " + alarm.getTime());
                } catch (QueueOverflowException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                if(result == JOptionPane.OK_OPTION) {
                    System.out.println("Hours: " + hours.getText());
                    System.out.println("Minutes: " + minutes.getText());
                    System.out.println("Seconds: " + seconds.getText());  
                }
                
                try {
                    System.out.println(pq.head());
                } catch (QueueUnderflowException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println(pq.toString());
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Edit an alarm...");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("IT WORKS");
                String nameOut = alarm.getName();
                int timeInSeconds = alarm.getTime();
                int hoursInt = timeInSeconds / 3600;
                String hoursString = "" + hoursInt;

                JTextField name = new JTextField(nameOut);
                JTextField hours = new JTextField(hoursString);
                JTextField minutes = new JTextField(5);
                JTextField seconds = new JTextField(5);

                alarm = new Alarm();
                

                JPanel inputPanel = new JPanel();

                inputPanel.add(new JLabel("Edit Name: "));
                inputPanel.add(name);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Edit Hours: "));
                inputPanel.add(hours);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Edit Minutes: "));
                inputPanel.add(minutes);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Edit Seconds: "));
                inputPanel.add(seconds);
                int result =  JOptionPane.showConfirmDialog(null, inputPanel, "Please fill in the details of the alarm below", JOptionPane.OK_CANCEL_OPTION);
            }
        });
        menu.add(menuItem);
        
        pane.add(menuBar, BorderLayout.PAGE_START);
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        button = new JButton("Button 3 (LINE_START)");
        //pane.add(button, BorderLayout.LINE_START);
         
        //button = new JButton("About");
        //pane.add(button, BorderLayout.PAGE_END);
 
      
                
        JLabel label = new JLabel("This is a label");
        pane.add(label, BorderLayout.PAGE_END);
        
        button = new JButton("5 (LINE_END)");
        //pane.add(button, BorderLayout.LINE_END);
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
