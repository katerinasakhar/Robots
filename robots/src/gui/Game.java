package gui;

import model.Robot;
import model.Target;

public class Game {
    public static final Game INSTANCE= new Game();
    private Robot robot =new Robot();
    private Target target=new Target();

    public Robot getRobot() {
        return robot;
    }

    public Target getTarget() {
        return target;
    }
}
