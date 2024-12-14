package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

public class Day14 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day14());

    }


    int doIt(Robot robot) {
        return 0;
    }

    @Override
    public Integer partOne(List<String> input) {
        return calculateSafetyFactory(input, 101, 103);
    }

    int calculateSafetyFactory(List<String> input, int tilesWide, int tilesTall) {
        var robots = input.stream().map(Robot::fromString).toList();
        printRobots(robots, tilesWide, tilesTall);
        var positions = robots.stream().map(robot -> moveRobot(robot, tilesWide, tilesTall, 100)).toList();
//        printPositions(positions, tilesWide, tilesTall);
        return getSafetyScore(positions, tilesWide, tilesTall).safetyFactor();
    }

    private Score getSafetyScore(List<Position> positions, int tilesWide, int tilesTall) {
        int topLeft = 0;
        int topRight = 0;
        int bottomLeft = 0;
        int bottomRight = 0;
        for (Position position : positions) {
            if (isTopLeft(position, tilesWide, tilesTall)) {
                topLeft++;
            } else if (isTopRight(position, tilesWide, tilesTall)) {
                topRight++;
            } else if (isBottomLeft(position, tilesWide, tilesTall)) {
                bottomLeft++;
            } else if (isBottomRight(position, tilesWide, tilesTall)) {
                bottomRight++;
            }
        }
        return new Score(topLeft, topRight, bottomLeft, bottomRight);

    }


    private boolean isTopLeft(Position position, int tilesWide, int tilesTall) {
        return position.x() < tilesWide / 2 && position.y() < tilesTall / 2;
    }

    private boolean isTopRight(Position position, int tilesWide, int tilesTall) {
        return position.x() > tilesWide / 2 && position.y() < tilesTall / 2;
    }

    private boolean isBottomLeft(Position position, int tilesWide, int tilesTall) {
        return position.x() < tilesWide / 2 && position.y() > tilesTall / 2;
    }

    private boolean isBottomRight(Position position, int tilesWide, int tilesTall) {
        return position.x() > tilesWide / 2 && position.y() > tilesTall / 2;
    }

    private Position moveRobot(Robot robot, int tilesWide, int tilesTall, int numberOfMove) {
        int newX = Math.floorMod(robot.position().x() + numberOfMove * robot.velocity().x(), tilesWide);
        int newY = Math.floorMod(robot.position().y() + numberOfMove * robot.velocity().y(), tilesTall);
        return new Position(newX, newY);
    }

    private Position moveRobot(Position position, Velocity velocity, int tilesWide, int tilesTall, int numberOfMove) {
        int newX = Math.floorMod(position.x() + numberOfMove * velocity.x(), tilesWide);
        int newY = Math.floorMod(position.y() + numberOfMove * velocity.y(), tilesTall);
        return new Position(newX, newY);
    }


    private void printRobots(List<Robot> robots, int tilesWide, int tilesTall) {
        printPositions(robots.stream().map(Robot::position).toList(), tilesWide, tilesTall);
    }
    private void printPositions(List<Position> positions, int tilesWide, int tilesTall) {
        int[][] map = new int[tilesTall][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new int[tilesWide];
            Arrays.fill(map[i], 0);
        }
        positions.forEach(position -> map[position.y()][position.x()]++);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] == 0 ? '.' : "" + map[i][j]);
            }
            System.out.println();
        }

    }



    @Override
    public Integer partTwo(List<String> input) {
        var robots = input.stream().map(Robot::fromString).toList();
        boolean keepGoing = true;
        int count = 0;
        int max = 0;
        while (keepGoing) {
            robots = robots.stream().map(robot -> new Robot(
                    moveRobot(robot, 101, 103, 1),
                    robot.velocity())).toList();
//            var score = getSafetyScore(robots.stream().map(Robot::position).toList(), 101, 103);
//            keepGoing = score.topLeft() != 0 && score.topRight() != 0 && score.bottomLeft() != 0 && score.bottomRight() != 0;
            var lines = countLines(robots.stream().map(Robot::position).toList());
            var bool1 = max < lines;
            if (bool1) {
                max = lines;
                System.out.println(count);
                printRobots(robots, 101, 103);
                keepGoing = count < 1000;
            }
            count++;
        }
//        printRobots(robots, 101, 103);
//        for (int i = 0; i < 1000; i++) {
//            robots = robots.stream().map(robot -> new Robot(
//                    moveRobot(robot, 101, 103, 1),
//                    robot.velocity())).toList();
//            printRobots(robots, 101, 103);
//        }
        return count;
    }

    private int countLines(List<Position> positions) {
        Set<Position> savedPosition = new HashSet<>(positions);
        int count = 0;
        for (Position position : positions) {
            if (savedPosition.contains(new Position(position.x() - 1, position.y()))) count++;
            if (savedPosition.contains(new Position(position.x(), position.y() - 1))) count++;
            if (savedPosition.contains(new Position(position.x() + 1, position.y()))) count++;
            if (savedPosition.contains(new Position(position.x(), position.y() - 1))) count++;
        }
        return count;

    }


    private boolean isTreeCandidate(List<Robot> robots, int tilesWide, int tilesTall) {
        return robots
                .stream()
                .allMatch(robot -> !isTopLeft(robot.position(), tilesWide / 4, tilesTall / 4)
                        && !isTopRight(robot.position(), tilesWide / 4, tilesTall / 4))
                ;
    }

    record Score(int topLeft, int topRight, int bottomLeft, int bottomRight) {
        int safetyFactor() {
            return topLeft * topRight * bottomLeft * bottomRight;
        }

    }

    record Robot(Position position, Velocity velocity) {
        static Robot fromString(String input) {
            Scanner in = new Scanner(input).useDelimiter("[^\\-0-9]+");
            return
                    new Robot(
                            new Position(in.nextInt(), in.nextInt()),
                            new Velocity(in.nextInt(), in.nextInt()));
        }

    }

    record Position(int x, int y) {
    }

    record Velocity(int x, int y) {
    }
}
