package RobotsGame;

import java.awt.Color;

public abstract class BaseRobotsGameObject {
    int x;
    int y;
    Color color;
    Properties properties;
    int energy;
    boolean exist;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return Math.min(Math.max(5, energy), 20);
    }

    public Color getColor() {
        return color;
    }

    public Properties getProperties() {
        if(properties == null) return Properties.DEFAULT;
        return properties;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isExist() {
        return exist;
    }
}
