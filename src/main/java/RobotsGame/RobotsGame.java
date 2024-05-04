package RobotsGame;

import java.util.LinkedList;
import java.util.Observable;

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
            robots.add(
                new Robot(p.x, p.y, )
            );
        }
    }

}
