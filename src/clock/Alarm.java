package clock;

public class Alarm {

    protected String name;
    protected int time;

    public Alarm() {
        this.name = name;
        //this.hours = hours;
        //this.minutes = minutes;
        //this.seconds = seconds;   
        this.time = time;
    }

    public String getName() {
        return name;
    }
    
    public int getTime() {
        return time;
    }

    
    
   


    @Override
    public String toString() {
        String s = "\"Name: \" + name \"Time: \" + hours";
        return s;
    }
}
