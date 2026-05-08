public class Main {
    public static void main(String[] args){
        
        //instantiate appropriate classes & feed them into each other
        Model model = new Model();
        GUI gui = new GUI(model);
        Controller controller = new Controller(model, gui);

        //begin listening for user input
        gui.setController(controller);

    }
   
}
