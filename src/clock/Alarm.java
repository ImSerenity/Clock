package clock;

import queuemanager.PriorityQueue;
import queuemanager.SortedArrayPriorityQueue;

/**
 * Basic class for storing alarm data
 * @author2 Thomas Wood - 09004316
 */
public class Alarm {

    String name;
    int hours;
    int minutes;
    int priority;

    public Alarm(String name, int hours, int minutes, int priority) {
        this.name = name;
        this.hours = hours;
        this.minutes = minutes;
        //this.seconds = seconds;   
        this.priority = priority;
    }

    public String getName() {
        return name;
    }
    
    public int getPriority() {
        return priority;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    
    /**
     * Method for searching a name of a specific alarm based on user input for editing alarms.
     * @deprecated is not used
     * @param target name for search
     * @return true/false if found/not found respectively
     */
    public boolean searchName(String target) {
        if(target.equals(getName())) 
        {
            target = name;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return null;
    }
}
