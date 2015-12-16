package clock;

import queuemanager.PriorityQueue;
import queuemanager.SortedArrayPriorityQueue;

public class Alarm {

    String name;
    int time;
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
        return time;
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

    public void setTime(int time) {
        this.time = time;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    } 
    
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
