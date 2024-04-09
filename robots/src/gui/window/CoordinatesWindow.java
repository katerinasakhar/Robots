package gui.window;

import controler.GameController;
import gui.Game;
import model.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CoordinatesWindow extends JInternalFrame implements Observer {
    private GameController gameController;
    private JLabel label=new JLabel();
    public CoordinatesWindow(GameController model){

        super ("Координаты поля",true, true, true, true);
        gameController =model;
        Game.INSTANCE.getRobot().addObserver(this);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Robot) {
            changeText();
        }
    }
    private void changeText(){
        label.setText(Double.toString(Game.INSTANCE.getRobot().getPosition_X()) + " " + Double.toString(Game.INSTANCE.getRobot().getPosition_Y()));
    }
}
