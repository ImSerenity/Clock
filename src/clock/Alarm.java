package clock;

import javax.swing.JTextField;

public class Alarm {

    protected String name;
    protected int hours;
    protected int minutes;
    protected int seconds;

    public Alarm(String name, int hours, int minutes, int seconds) {
        this.name = name;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;     
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    
    

    @Override
    public String toString() {
        String s = "\"Name: \" + name \"Time: \" + hours";
        return s;
    }
}
