package model;

import java.util.Observable;

public abstract class Entity extends Observable {
    private volatile double position_X;
    private volatile double position_Y;
    public Entity (int x,int y){
        position_X =x;
        position_Y =y;
    }
    public double getPosition_X() {
        return position_X;
    }
    public double getPosition_Y() {
        return position_Y;
    }

    public void setPosition(double x, double y){
        position_X =x;
        position_Y =y;
        setChanged();
        notifyObservers();
        clearChanged();
    }
    public Boolean isEqual(Entity a){
        if ((Math.abs(position_X -a.position_X)<1)&&(Math.abs(position_Y -a.position_Y)<1)){
            return true;
        }
        return false;
    }
}
