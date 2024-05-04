package RobotsGame;

import java.awt.*;

public class Robot extends BaseRobotsGameObject {
    double speed;
    double seeRange;
    public Robot(double x, double y, int size, double seeRange, double speed, Color color, Properties properties, int energy)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.color = color;
        this.properties = properties;
        this.energy = energy;
        this.seeRange = seeRange;
        this.exist = true;
    }
}