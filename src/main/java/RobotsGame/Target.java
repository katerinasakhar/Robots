package RobotsGame;

import java.awt.Color;

public class Target extends BaseRobotsGameObject {
    public Target(double x, double y, int size, Color color, Properties properties, int energy)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.properties = properties;
        this.energy = energy;
        this.exist = true;
    }

}
