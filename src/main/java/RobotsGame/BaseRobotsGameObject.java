package RobotsGame;

import java.awt.Color;

public abstract class BaseRobotsGameObject {
    double x;
    double y;
    int size;
    Color color;
    Properties properties;
    int energy;
    boolean exist;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public Properties getProperties() {
        return properties;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isExist() {
        return exist;
    }
}
