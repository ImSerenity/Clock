package clock;
//Imports
import com.sun.media.sound.JARSoundbankReader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;
import queuemanager.SortedArrayPriorityQueue;

/**
 * Main class for everything the user will see and interact with
 * @author Ian Barnes
 * @author2 Thomas Wood - 09004316
 */
public class View implements Observer, ActionListener {
    /**
     * Declare Clock Panel
     */
    ClockPanel panel;
    /**
     * A new empty alarm object
     */
    Alarm alarm = new Alarm("", 0, 0, 0);
    /**
     * A new Priority Queue object with 100 array spaces available
     */
    queuemanager.SortedArrayPriorityQueue pq = new SortedArrayPriorityQueue<>(100);
    /**
     * A save button declaration for saving alarms
     */
    JButton saveButton;
    /**
     * Declaration for a new file chooser for saving alarms
     */
    JFileChooser jfc;
    /**
     * Declaration for a log area when saving alarms
     */
    JTextArea log;
    
    /**
     * Main method for everything the user will see and interact with.
     * Includes other views and interaction methods.
     * Declares the title of the main view and various other features such as the border layout.
     * @param model defines the model to be used for the panel
     */
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
        
        /**
         * Declares the main MenuBar item
         */
        JMenuBar menuBar;
        /**
         * Declares the main Menu= item
         */
        JMenu menu;
        /**
         * Declares a MenuItem
         */
        JMenuItem menuItem;

        menuBar = new JMenuBar();
        
        menu = new JMenu("Main Menu");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Set an alarm...");
        menuItem.addActionListener(new ActionListener() {
            /**
             * Method for creating what happens when the user selects the 'Set an alarm...' menu item.
             * Allows the user to input a name, hour and minute for the alarm to be set as.
             * @param e 
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField(5);
                JTextField hours = new JTextField(5);
                JTextField minutes = new JTextField(5);
                JPanel inputPanel = new JPanel();

                inputPanel.add(new JLabel("Name: "));
                inputPanel.add(name);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Hours: "));
                inputPanel.add(hours);
                inputPanel.add(Box.createHorizontalStrut(15));
                inputPanel.add(new JLabel("Minutes: "));
                inputPanel.add(minutes);

                int result =  JOptionPane.showConfirmDialog(null, inputPanel, "Please fill in the details of the alarm below", JOptionPane.OK_CANCEL_OPTION);
                
                String hoursOut = hours.getText();
                int hoursOutInt = Integer.parseInt(hoursOut);
                String minutesOut = minutes.getText();
                int minutesOutInt = Integer.parseInt(minutesOut);
                String nameOut = name.getText();
                Calendar cal = Calendar.getInstance();
                int timeInSeconds = (hoursOutInt * 60 * 60) + (minutesOutInt * 60);
                int currentTime = cal.get(Calendar.HOUR_OF_DAY) * 60;
                
                /**
                 * Create the priority number for the PQ.
                 */
                int priority = currentTime - timeInSeconds; 
                //alarm.setHours(hoursOutInt);
                //alarm.setMinutes(minutesOutInt);
                //alarm.setSeconds(secondsOutInt);
                
                /**
                 * Creates a new alarm object with the data input by the user and the calculated priority.
                 * Adds the data to the priority queue.
                 */
                alarm = new Alarm(nameOut, hoursOutInt, minutesOutInt, priority);
                try {
                    pq.add(nameOut, priority);
                    System.out.println("Adding alarm with Name: " + alarm.getName() + " and Time: " + alarm.getHours() + ":" + alarm.getMinutes());
                    System.out.println("Next alarm is: " + pq.head());
                } catch (QueueOverflowException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                } catch (QueueUnderflowException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                if(result == JOptionPane.OK_OPTION) {
                    //The print statements below were for debugging purposes
                    //System.out.println("Hours: " + hours.getText());
                    //System.out.println("Minutes: " + minutes.getText()); 
                }           
                
                System.out.println(pq.toString());
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Edit an alarm...");
        menuItem.addActionListener(new ActionListener() {
            /**
             * Allows the user to edit an alarm that they have created.
             * Autofills the fields for editing based on the alarm chosen.
             * Users choose an alarm from a dropdown box and then edit the fields.
             * **Does not work**
             * @param e 
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameOut = alarm.getName();
                int hoursOut = alarm.getHours();
                int minutesOut = alarm.getMinutes();
                
                String hoursString = "" + hoursOut;
                String minutesString = "" + minutesOut;

                
                JTextField name = new JTextField(nameOut);
                JTextField hours = new JTextField(hoursString);
                JTextField minutes = new JTextField(minutesString);

                //alarm = new Alarm(nameOut, hoursOut, minutesOut, hoursInt);
                
                JPanel inputPanel = new JPanel();
                JPanel inputPanel2 = new JPanel();
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                //for(int i = 0; i < 100; i++)
                //{
                    model.addElement(alarm.getName());
                //}
                JOptionPane option = new JOptionPane("Choose an alarm");
                JComboBox combo = new JComboBox(model);
                inputPanel2.add(combo);
                inputPanel2.add(Box.createHorizontalStrut(15));
                int result =  JOptionPane.showConfirmDialog(null, inputPanel2, "Please choose an alarm", JOptionPane.OK_CANCEL_OPTION);

                String target = "" + combo;
                if(result == JOptionPane.OK_OPTION) {
                    

                    inputPanel.add(new JLabel("Edit Name: "));
                    inputPanel.add(name);
                    inputPanel.add(Box.createHorizontalStrut(15));
                    inputPanel.add(new JLabel("Edit Hours: "));
                    inputPanel.add(hours);
                    inputPanel.add(Box.createHorizontalStrut(15));
                    inputPanel.add(new JLabel("Edit Minutes: "));
                    inputPanel.add(minutes);

                    String nameIn = name.getText();
                    String hoursIn = hours.getText();
                    String minutesIn = hours.getText();
                    int hoursInInt = Integer.parseInt(hoursIn);
                    int minutesInInt = Integer.parseInt(minutesIn);
                    alarm.setName(nameIn);
                    alarm.setHours(hoursInInt);
                    alarm.setMinutes(minutesInInt);
                    Calendar cal = Calendar.getInstance();
                    int timeInSeconds = (hoursInInt * 60 * 60) + (minutesInInt * 60);
                    int currentTime = cal.get(Calendar.HOUR_OF_DAY) * 60;
                    int priority = currentTime - timeInSeconds; 
                    alarm = new Alarm(nameIn, hoursInInt, minutesInInt, priority);
                    int result2 =  JOptionPane.showConfirmDialog(null, inputPanel, "Please fill in the details of the alarm below", JOptionPane.OK_CANCEL_OPTION);
                
                    if(result2 == JOptionPane.OK_OPTION) {
                        System.out.println("Edited alarm to Name: " + alarm.getName() + " and Time: " + alarm.getHours() + ":" + alarm.getMinutes());
                        System.out.println(pq.toString());
                    }
                }
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Delete an alarm...");
        menuItem.addActionListener(new ActionListener() {
            /**
             * Allows the user to delete an alarm that they have created.
             * Users choose an alarm from a dropdown box and then click the button to delete it
             * **Works to an extent**
             * @param e 
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameOut = alarm.getName();

                JPanel inputPanel3 = new JPanel();
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                //for(int i = 0; i < 100; i++)
                //{
                    model.addElement(alarm.getName());
                //}
                JOptionPane option = new JOptionPane("Choose an alarm");
                JComboBox combo = new JComboBox(model);
                inputPanel3.add(combo);
                inputPanel3.add(Box.createHorizontalStrut(15));
                int result =  JOptionPane.showConfirmDialog(null, inputPanel3, "Please choose an alarm to delete", JOptionPane.OK_CANCEL_OPTION);

                if(result == JOptionPane.OK_OPTION) {
                    try {
                        pq.remove();
                        System.out.println("Deleting alarm: " + alarm.getName());
                        System.out.println(pq.toString());
                    } catch (QueueUnderflowException ex) {
                        Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Exit.");
        menuItem.addActionListener(new ActionListener() {
            /**
             * Allows the user to safely exit the program without losing data.
             * Prompts the user to save their alarms when clicked by choosing a directory and name for the file.
             * @param e 
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputPanel = new JPanel();
                log = new JTextArea(5, 20);
                log.setMargin(new Insets(5,5,5,5));
                log.setEditable(false);
                jfc = new JFileChooser();
                saveButton = new JButton("Save a File...");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int returnVal = jfc.showSaveDialog(panel);
                        if(returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = jfc.getSelectedFile();
                            saveToFile(file);
                            log.append("Saving: " + file.getName());
                        }
                    }
                });
                
                inputPanel.add(saveButton);
                inputPanel.add(log);
                panel.add(inputPanel);
                int result3 =  JOptionPane.showConfirmDialog(null, inputPanel, "Choose an option", JOptionPane.OK_CANCEL_OPTION);
            }
        });
        /**
         * Add the menu item to the menu
         */
        menu.add(menuItem);
        
        /**
         * Add the menubar to the program and sets the default dimensions of the main window
         */
        pane.add(menuBar, BorderLayout.PAGE_START);
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        /**
         * Gets the head of the queue
         */
        try {
            //pq.add("Test", 0);
            pq.head(); 
            System.out.println(alarm.getName());
        } catch (QueueUnderflowException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
         * Creates a label at the bottom of the main window to display the next alarm
         * **Alarm details not displaying correctly**
         */
        String name = alarm.getName();
        
        JLabel label = new JLabel("Next alarm is: " + name + " with time: " + alarm.getHours() + ":" + alarm.getMinutes());     

        pane.add(label, BorderLayout.PAGE_END);
        panel.repaint();     
        
        //End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Repaints the window to update the clock hand display
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        panel.repaint();       
    }
    
    /**
     * Standard required constructor - not used as done inline
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
    /**
     * Method to calculate when an alarm time is reached and display a pop up accordingly.
     * This method would normally pull DTSTART and DTEND from an iCal file and use them for the user inputted alarm data.
     * but a load file method was not created.
     * Pop up box stuck until the minute has passed -  re runs remove statement.
     * **This is an error**
     */
    public void alarm() {
        Calendar date2 = Calendar.getInstance();
        int DTSTART = alarm.getHours();
        if(DTSTART == date2.get(Calendar.HOUR_OF_DAY) && date2.get(Calendar.MINUTE) == alarm.getMinutes())
        {
            JPanel inputPanel = new JPanel();
            JOptionPane.showConfirmDialog(null, inputPanel, "ALARM: " + alarm.getName(), JOptionPane.OK_CANCEL_OPTION);
            System.out.println("Time reached");
            try {
                pq.remove();
            } catch (QueueUnderflowException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Method to save an ics file of alarms to a user designated directory.
     * @param fileName for designating the name of the file to be saved
     */
    public void saveToFile(File fileName) {
        int hoursInt = alarm.getHours();
        String hours = "" + hoursInt;
        String minutes = "" + alarm.getMinutes();
        
        try {
            String str = "BEGIN:VCALENDAR\r\nBEGIN:VEVENT\r\nDTSTART:"+hours+":"+minutes+"\r\nDTEND:"+hours+":"+minutes+"\r\nACTION:DISPLAY\r\nDESCRIPTION:\r\nEND:VEVENT\r\nEND:VCALENDAR";
            FileWriter fw = new FileWriter(fileName+".ics");
            fw.write(str);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
