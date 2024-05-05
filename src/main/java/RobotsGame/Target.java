package RobotsGame;

import java.awt.Color;

public class Target extends BaseRobotsGameObject {
    public Target(int x, int y, Color color, Properties properties, int energy)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.properties = properties;
        this.energy = energy;
        this.exist = true;
    }

}
