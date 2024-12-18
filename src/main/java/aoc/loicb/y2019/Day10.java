package aoc.loicb.y2019;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

public class Day10 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day10());

    }

    @Override
    public Integer partOne(List<String> input) {
        var asteroids = findAsteroids(input);
        int max = 0;
        for (int i = 0; i < asteroids.size(); i++) {
            Set<Double> angles = new HashSet<>();
            for (int j = 0; j < asteroids.size(); j++) {
                if (i != j) {
                    angles.add(calculateAngle(asteroids.get(i), asteroids.get(j)));
                }
            }
            max = Math.max(max, angles.size());
        }
        return max;
    }

    private double calculateAngle(CelestialObject asteroid1, CelestialObject asteroid2) {
        double angle = Math.toDegrees(Math.atan2(asteroid2.x() - asteroid1.x(), asteroid2.y() - asteroid1.y()));
        angle = angle + Math.ceil(-angle / 360) * 360;
        return angle;
    }

    private List<CelestialObject> findAsteroids(List<String> input) {
        List<CelestialObject> asteroids = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    asteroids.add(new CelestialObject(j, i));
                }
            }
        }
        return asteroids;
    }

    @Override
    public Integer partTwo(List<String> input) {
        return findAsteroid(200, input);
    }

    protected int findAsteroid(int n, List<String> input) {
        var asteroids = findAsteroids(input);
        int max = 0;
        int index = -1;
        for (int i = 0; i < asteroids.size(); i++) {
            Set<Double> angles = new HashSet<>();
            for (int j = 0; j < asteroids.size(); j++) {
                if (i != j) {
                    angles.add(calculateAngle(asteroids.get(i), asteroids.get(j)));
                }
            }
            if (max < angles.size()) {
                max = angles.size();
                index = i;
            }
            max = Math.max(max, angles.size());
        }
        var station = asteroids.get(index);
        asteroids.remove(station);
//        var station = findMonitoringStation(input).orElse(new CelestialObject(11,13));
        Map<Double, List<CelestialObject>> angleToObject = new HashMap<>();
        asteroids.forEach(asteroid -> {
            double tmpAngle = calculateAngle(asteroid, station);
            double angle = tmpAngle == 0 ? 360.0 : tmpAngle;
            if (!angleToObject.containsKey(angle)) {
                angleToObject.put(angle, new ArrayList<>());
            }
            var list = angleToObject.get(angle);
            list.add(asteroid);
            list.sort((o1, o2) -> {
                double d = calculateDistance(station, o1) - calculateDistance(station, o2);
                if (d < 0) return -1;
                if (d > 0) return 1;
                return 0;
            });
            angleToObject.put(angle, list);
        });
        System.out.println(angleToObject);
        var angles = new ArrayList<>(angleToObject.keySet());
        angles.sort((o1, o2) -> {
            double d = o2 - o1;
            if (d < 0) return -1;
            if (d > 0) return 1;
            return 0;
        });
        System.out.println(angles);
        int cpt = 0;
        int round = 0;
        while (cpt < n) {
//            if (angleToObject.get(0.0).size() < round) {
//                cpt ++;
//            }
            for (int i = 0; i < angles.size(); i++) {
                if (angleToObject.get(angles.get(i)).size() >= round) {
                    cpt++;
                    System.out.println(angleToObject.get(angles.get(i)).get(round));
                    if (cpt == n) return calculateValue(angleToObject.get(angles.get(i)).get(round));
                }
            }
            round++;
        }
        return 0;
    }

    private int calculateValue(CelestialObject object) {
        return 100 * object.x() + object.y();
    }

    private double calculateDistance(CelestialObject object1, CelestialObject object2) {
        return Math.hypot(object1.x() - object2.x(), object1.y() - object2.y());
    }


    private Optional<CelestialObject> findMonitoringStation(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == 'X') {
                    return Optional.of(new CelestialObject(j, i));
                }
            }
        }
        return Optional.empty();
    }

    record CelestialObject(int x, int y) {
    }
}
