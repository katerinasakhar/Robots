package RobotsGame;

import gui.Game;
import gui.RobotsProgram;

import java.awt.*;
import java.util.*;

public class RobotsGame extends Observable {
    private final Timer timer = new Timer("events generator", true);
    private final LinkedList<Target> targets = new LinkedList<>();
    private final LinkedList<Robot> robots = new LinkedList<>();
    private final int length;
    private final int width;

    private static class Point {
        Point(int a, int b) {
            x = a;
            y = b;
        }
        int x;
        int y;
    }

    private Point getRandomPoint() {
        return new Point(
            (int) (Math.random() * (length)),
            (int) (Math.random() * (length))
        );
    }

    private Target getRandomTarget() {
        Point p = getRandomPoint();
        return new Target(p.x, p.y, Color.GRAY, Properties.getRandomProperties(), (int) (Math.random() * 100));
    }

    private Robot getRandomRobot(Properties properties) {
        Point p = getRandomPoint();
        Robot robot = new Robot(p.x, p.y, 10000, 1, Color.WHITE, properties, 100);
        robot.properties.apply(robot);
        return robot;
    }

    public RobotsGame(int numberOfRobots, int numberOfTargets, int length, int width) {
        this.length = length;
        this.width = width;
        for(int i = 0; i < numberOfRobots; i++) {
            robots.add(getRandomRobot(Properties.getRandomProperties()));
        }
        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(getRandomTarget());
        }

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        next();
                        LinkedList<BaseRobotsGameObject> tmp = new LinkedList<>();
                        synchronized (timer) {
                            tmp.addAll(targets);
                            tmp.addAll(robots);
                        }
                        setChanged();
                        notifyObservers(tmp);
                    }
                },
                1000,
                100
        );
    }

    private int getDistance(BaseRobotsGameObject obj1, BaseRobotsGameObject obj2) {
        int difX = obj1.x - obj2.x;
        int difY = obj1.y - obj2.y;
        return difX*difX + difY*difY;
    }

    private Target getRobotTarget(Robot robot) {
        int minDistance = length*length+width*width+1;
        Target closestTarget = null;
        for(Target target: targets) {
            if(target.isExist()) {
                int dist = getDistance(robot, target);
                if (dist < robot.seeRange && dist < minDistance) {
                    minDistance = dist;
                    closestTarget = target;
                }
            }
        }
        return closestTarget;
    }

    private void moveRobotToTarget(Robot robot, Target target) {
        int dist = getDistance(robot, target);
        robot.energy--;
        robot.ttl--;
        if(dist <= robot.getSpeed()*robot.getSpeed()) {
            robot.x = target.x;
            robot.y = target.y;
            if(target.isExist()) {
                targets.remove(target);
                targets.add(getRandomTarget());
                target.exist = false;
                robot.energy += target.energy;
                target.getProperties().apply(robot);
            }
        }
        else {
            int difX = target.x-robot.x;
            int difY = target.y-robot.y;
            int newX = (int)(robot.x + robot.getSpeed()/(Math.sqrt(dist)-Math.random()/10)*difX);
            int newY = (int)(robot.y + robot.getSpeed()/(Math.sqrt(dist)-Math.random()/10)*difY);
            robot.x = newX;
            robot.y = newY;
        }
    }
    private void next() {
        synchronized (timer) {
            if (robots.isEmpty()) {
                timer.cancel();
                return;
            }
            LinkedList<Robot> robots1 = new LinkedList<>();
            for (Robot robot : robots) {
                if (robot.energy > 0) {
                    Target target = getRobotTarget(robot);
                    if (target == null) {
                        Point p = getRandomPoint();
                        target = new Target(p.x, p.y, Color.GRAY, Properties.DEFAULT, 0);
                        target.exist = false;
                    }
                    moveRobotToTarget(robot, target);
                    if(robot.ttl <= 0 && robot.energy >= 100) {
                        Robot robot1 = getRandomRobot(robot.properties);
                        Robot robot2 = getRandomRobot(robot.properties);
                        robot1.energy = robot.energy/2;
                        robot1.x = robot.x+5;
                        robot1.y = robot.y+5;
                        robot2.energy = robot.energy/2;
                        robot2.x = robot.x-5;
                        robot2.y = robot.y-5;
                        robots1.add(robot1);
                        robots1.add(robot2);
                    }
                    else {
                        robots1.add(robot);
                    }
                }
            }
            robots.clear();
            robots.addAll(robots1);
        }
    }

}
