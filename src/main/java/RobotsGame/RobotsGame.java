package RobotsGame;

import gui.Game;

import java.awt.*;
import java.util.*;

public class RobotsGame extends Observable {
    private final LinkedList<Target> targets = new LinkedList<>();
    private final LinkedList<Robot> robots = new LinkedList<>();
    private final int length;
    private final int width;

    private class Point {
        Point(double a, double b) {
            x = a;
            y = b;
        }
        double x;
        double y;
    }

    private Point getRandomPoint() {
        return new Point(
        (double) (int) (Math.random() * (length) * 100) / 100,
        (double) (int) (Math.random() * (length) * 100) / 100
        );
    }

    public RobotsGame(int numberOfRobots, int numberOfTargets, int length, int width) {
        this.length = length;
        this.width = width;
        for(int i = 0; i < numberOfRobots; i++) {
            Point p = getRandomPoint();
            Robot robot = new Robot(p.x, p.y, 5, 5, Color.GRAY, Properties.getRandomProperties(), 10);
            robot.properties.apply(robot);
            robots.add(robot);
        }
        for(int i = 0; i < numberOfTargets; i++) {
            Point p = getRandomPoint();
            Target target = new Target(p.x, p.y, Color.GRAY, Properties.getRandomProperties(), (int) (Math.random() * 15));
            targets.add(target);
        }
        var timer = new Timer("events generator", true);
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        next();
                    }
                },
                3000,
                100
        );
    }

    private double getDistance(BaseRobotsGameObject obj1, BaseRobotsGameObject obj2) {
        double difX = obj1.x - obj2.x;
        double difY = obj1.y - obj2.y;
        return difX*difX + difY*difY;
    }


    private void next() {
        for(Robot robot: robots) {

        }
    }

}
