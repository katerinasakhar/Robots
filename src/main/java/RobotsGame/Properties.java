package RobotsGame;

import java.awt.Color;
import java.util.LinkedList;

import static java.lang.Math.*;

public class Properties implements Property {
    public static final Properties DEFAULT = new Properties();
    private LinkedList<Property> properties = new LinkedList<>();
    @Override
    public void apply(Robot robot) {
        for(Property property: properties) {
            property.apply(robot);
        }
    }

    void addProperty(Property property) {
        properties.add(property);
    }
    static Property getRandomProperty() {
        Property[] randomProperties = {
            (Robot robot) -> {
                robot.color = Color.BLACK;
            },
            (Robot robot) -> {
                robot.color = Color.BLUE;
            },
            (Robot robot) -> {
                robot.color = Color.RED;
            },
            (Robot robot) -> {
                robot.color = Color.GREEN;
            },
            (Robot robot) -> {
                robot.color = Color.YELLOW;
            },
            (Robot robot) -> {
                robot.color = Color.CYAN;
            },
            (Robot robot) -> {
                robot.speed *= 2;
            },
            (Robot robot) -> {
                robot.speed *= 1.5;
            },
            (Robot robot) -> {
                robot.speed /= 2;
            },
            (Robot robot) -> {
                robot.speed /= 3;
            },
            (Robot robot) -> {
                robot.speed *= 2.5;
            },
            (Robot robot) -> {
                robot.speed *= 3;
            },
            (Robot robot) -> {
                robot.speed /= 1.5;
            },
            (Robot robot) -> {
                robot.speed /= 2.5;
            },
            (Robot robot) -> {
                robot.seeRange *= 2;
            },
            (Robot robot) -> {
                robot.seeRange /= 2;
            },
            (Robot robot) -> {
                robot.seeRange *= 4;
            },
            (Robot robot) -> {
                robot.seeRange /= 3;
            },
            (Robot robot) -> {
                robot.ttl /= 3;
            },
            (Robot robot) -> {
                robot.ttl *= 3;
            },
            (Robot robot) -> {
                robot.energy = 0;
            }
        };

        return new Property() {
            private final int mutationChance = (int) (random() * 100);
            private final Property property = randomProperties[((int)(random()*1000)) % randomProperties.length];
            @Override
            public void apply(Robot robot) {
                if((int)(random()*100) > mutationChance) {
                    property.apply(robot);
                }
            }
        };
    }

    static Properties getRandomProperties() {
        Properties properties = new Properties();
        for(int i = 0; i < (int) (random() * 10); i++) {
            properties.addProperty(getRandomProperty());
        }

        return properties;
    }
}
