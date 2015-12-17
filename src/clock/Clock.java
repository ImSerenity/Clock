package clock;

/**
 * Main class to run everything needed to run
 * @author Ian Barnes
 */
public class Clock {
    
    /**
     * Main method - runs everything that is needed to run
     * @param args 
     */
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        model.addObserver(view);
        Controller controller = new Controller(model, view);   
    }
}
