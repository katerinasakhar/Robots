package model;

public class Robot extends Entity {
    private volatile double diraction;
    public Robot(){
        super(100,100);
        diraction =0;
    }

    public double getDiraction() {
        return diraction;
    }

    public void setDiraction(double diraction) {
        this.diraction = diraction;
    }
}
