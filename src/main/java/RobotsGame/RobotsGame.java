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
                        LinkedList<BaseRobotsGameObject> tmp = new LinkedList<>();
                        tmp.addAll(targets);
                        tmp.addAll(robots);
                        setChanged();
                        notifyObservers(tmp);
                    }
                },
                2000,
                100
        );
    }

    private double getDistance(BaseRobotsGameObject obj1, BaseRobotsGameObject obj2) {
        double difX = obj1.x - obj2.x;
        double difY = obj1.y - obj2.y;
        return difX*difX + difY*difY;
    }

    private Target getRobotTarget(Robot robot) {
        double minDistance = length*length+width*width+1;
        Target closestTarget = null;
        for(Target target: targets) {
            if(target.isExist()) {
                double dist = getDistance(robot, target);
                if (dist < robot.seeRange && dist < minDistance) {
                    minDistance = dist;
                    closestTarget = target;
                }
            }
        }
        return closestTarget;
    }

    private void moveRobotToTarget(Robot robot, Target target) {
        double dist = getDistance(robot, target);
        robot.energy -= 1;
        if(Math.abs(dist - robot.speed) < 0.01) {
            robot.x = target.x;
            robot.y = target.y;
            if(target.isExist()) {
                targets.remove(target);
                target.exist = false;
                robot.energy += target.energy;
                target.getProperties().apply(robot);
            }
        }
        else {
            double difX = target.x-robot.x;
            double difY = target.y-robot.y;
            double newX = robot.x + robot.speed*difX/Math.sqrt(dist);
            double newY = robot.y + robot.speed*difY/Math.sqrt(dist);
            robot.x = newX;
            robot.y = newY;
        }
    }
    private void next() {
        for(Robot robot: robots) {
            if(robot.energy > 0) {
                Target target = getRobotTarget(robot);
                if (target == null) {
                    Point p = getRandomPoint();
                    target = new Target(p.x, p.y, Color.GRAY, Properties.DEFAULT, 0);
                    target.exist = false;
                }
                moveRobotToTarget(robot, target);
            }
            else {
                robots.remove(robot);
            }
        }
    }

}
