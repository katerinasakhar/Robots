package controler;
import gui.Game;
import model.Robot;
import model.Target;
import view.GameVisualizer;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements Observer
{
     private Timer timer = initTimer();
     private static Timer initTimer()
     {
         return new Timer("events generator", true);
     }
    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.01;
    private static final double duration = 10;
    public GameVisualizer gameVisualizer = new GameVisualizer();

    public GameController()
    {
        Game.INSTANCE.getTarget().addObserver(this);
    }

    private static double distance(Target t, Robot r)
    {
        double diffX = t.getPosition_X() - r.getPosition_X();
        double diffY = t.getPosition_Y() - r.getPosition_Y();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
    public void onModelUpdateEvent()
    {
        double distance = distance(Game.INSTANCE.getTarget(), Game.INSTANCE.getRobot());
        if (distance < 0.5)
        {
            return;
        }
        double angleToTarget = angleTo(Game.INSTANCE.getRobot(),Game.INSTANCE.getTarget() );
        double angleDiff=angleToTarget-Game.INSTANCE.getRobot().getDiraction();
        if (angleDiff>Math.PI){
            angleDiff-=2*Math.PI;
        }
        else if(angleDiff<-Math.PI){
            angleDiff+=2*Math.PI;
        }
        double angularVelocity = applyLimits(angleDiff / duration, -maxAngularVelocity, maxAngularVelocity);
        double velocity=Math.min(distance/duration,maxVelocity);
        moveRobot(velocity, angularVelocity, duration);
        gameVisualizer.onRedrawEvent();
    }
    private static double angleTo(Robot r, Target t)
    {
        double diffX = t.getPosition_X() - r.getPosition_X();
        double diffY = t.getPosition_Y() - r.getPosition_Y();
        return asNormalizedRadians(Math.atan2(diffY,diffX));
    }
    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = Game.INSTANCE.getRobot().getPosition_X() + velocity / angularVelocity *
                (Math.sin(Game.INSTANCE.getRobot().getDiraction()  + angularVelocity * duration) -
                        Math.sin(Game.INSTANCE.getRobot().getDiraction()));
        if (!Double.isFinite(newX))
        {
            newX = Game.INSTANCE.getRobot().getPosition_X() + velocity * duration * Math.cos(Game.INSTANCE.getRobot().getDiraction());
        }

        double newY = Game.INSTANCE.getRobot().getPosition_Y() - velocity / angularVelocity *
                (Math.cos(Game.INSTANCE.getRobot().getDiraction()  + angularVelocity * duration) -
                Math.cos(Game.INSTANCE.getRobot().getDiraction()));
        if (!Double.isFinite(newY))
        {
            newY = Game.INSTANCE.getRobot().getPosition_Y() + velocity * duration * Math.sin(Game.INSTANCE.getRobot().getDiraction());
        }


        Game.INSTANCE.getRobot().setPosition(newX,newY);
        double newDiract=asNormalizedRadians(Game.INSTANCE.getRobot().getDiraction() + angularVelocity * duration);
        Game.INSTANCE.getRobot().setDiraction(newDiract);
    }
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)

            return min;
        if (value > max)
            return max;
        return value;
    }
    private static double asNormalizedRadians(double angle)
    {

        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }

        return angle;
    }


   @Override
    public void update(Observable o, Object arg) {
       if (o instanceof Target) {
           timer.cancel();
           timer = initTimer();
           timer.schedule(
                   new TimerTask() {
                       @Override
                       public void run() {
                           onModelUpdateEvent();
                           if(Game.INSTANCE.getRobot().isEqual(Game.INSTANCE.getTarget()))
                           {
                               timer.cancel();
                           }
                       }
                   },
                   0,
                   10
           );
       }
    }
}
