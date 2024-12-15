package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.List;

public class Day15 implements Day<Day15Input, Integer> {

    public static void main(String[] args) {
        DayExecutor<Day15Input> de = new DayExecutor<>(new Day15InputTransformer());
        de.execute(new Day15());
    }

    @Override
    public Integer partOne(Day15Input input) {
        char[][] map = copyMap(input.map());
        var position = findPosition(map);
        for (char direction : input.movements()) {
            position = moveRobot(map, position, direction);
        }
        return calculateBoxes(map);
    }

    int calculateBoxes(char[][] map) {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (isBox(map, j, i)) {
                    sum += (100 * i + j);
                }
            }
        }
        return sum;
    }


    Position moveRobot(char[][] map, Position robotPosition, char direction) {
        if (canMoveObject(map, direction, robotPosition.x(), robotPosition.y())) {
            moveObject(map, direction, robotPosition.x(), robotPosition.y());
            return switch (direction) {
                case '<' -> new Position(robotPosition.x() - 1, robotPosition.y());
                case '^' -> new Position(robotPosition.x(), robotPosition.y() - 1);
                case '>' -> new Position(robotPosition.x() + 1, robotPosition.y());
                case 'v' -> new Position(robotPosition.x(), robotPosition.y() + 1);
                default -> robotPosition;
            };
        }
        return robotPosition;

    }

    boolean canMoveObject(char[][] map, char direction, int fromX, int fromY) {
        int toX = getNextX(direction, fromX);
        int toY = getNextY(direction, fromY);
        if (isInside(map, toX, toY)) return false;
        if (isWall(map, toX, toY)) return false;
        if (isBox(map, toX, toY)) return canMoveObject(map, direction, toX, toY);
        if (isLargeBox(map, toX, toY)) {
            if (direction == '<' || direction == '>') return canMoveObject(map, direction, toX, toY);
            if (map[toY][toX] == ']')
                return (canMoveObject(map, direction, toX - 1, toY) && canMoveObject(map, direction, toX, toY));
            if (map[toY][toX] == '[')
                return (canMoveObject(map, direction, toX + 1, toY) && canMoveObject(map, direction, toX, toY));
        }
        return true;
    }


    void moveObject(char[][] map, char direction, int fromX, int fromY) {
        int toX = getNextX(direction, fromX);
        int toY = getNextY(direction, fromY);
        if (isBox(map, toX, toY)) moveObject(map, direction, toX, toY);
        if (isLargeBox(map, toX, toY)) {
            if (direction == '<' || direction == '>') {
                moveObject(map, direction, toX, toY);
            } else if (map[toY][toX] == ']') {
                moveObject(map, direction, toX - 1, toY);
                moveObject(map, direction, toX, toY);
            } else if (map[toY][toX] == '[') {
                moveObject(map, direction, toX + 1, toY);
                moveObject(map, direction, toX, toY);
            }
        }
        char obj = map[fromY][fromX];
        map[fromY][fromX] = map[toY][toX];
        map[toY][toX] = obj;
    }

    private boolean isWall(char[][] map, int x, int y) {
        return map[y][x] == '#';
    }

    private boolean isBox(char[][] map, int x, int y) {
        return map[y][x] == 'O';
    }

    private boolean isLargeBox(char[][] map, int x, int y) {
        return map[y][x] == '[' || map[y][x] == ']';
    }

    private boolean isRobot(char[][] map, int x, int y) {
        return map[y][x] == '@';
    }

    private boolean isInside(char[][] map, int x, int y) {
        return y < 0 || y >= map.length || x < 0 || x >= map[y].length;
    }

    int getNextX(char direction, int fomX) {
        return switch (direction) {
            case '<' -> fomX - 1;
            case '>' -> fomX + 1;
            default -> fomX;
        };
    }

    int getNextY(char direction, int fomY) {
        return switch (direction) {
            case '^' -> fomY - 1;
            case 'v' -> fomY + 1;
            default -> fomY;
        };
    }

    char[][] copyMap(char[][] map) {
        char[][] newMap = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            char[] aMap = map[i];
            int aLength = aMap.length;
            newMap[i] = new char[aLength];
            System.arraycopy(aMap, 0, newMap[i], 0, aLength);
        }
        return newMap;
    }


    Position findPosition(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (isRobot(map, j, i)) {
                    return new Position(j, i);
                }
            }
        }
        throw new RuntimeException("No robot found");
    }

    @Override
    public Integer partTwo(Day15Input input) {
        char[][] map = generateMap(input.map());
        var position = findPosition(map);
        for (char direction : input.movements()) {
            position = moveRobot(map, position, direction);
        }
        return calculateBoxesPartTwo(map);
    }


    int calculateBoxesPartTwo(char[][] map) {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '[') {
                    sum += (100 * i + j);
                }
            }
        }
        return sum;
    }

    char[][] generateMap(char[][] map) {
        char[][] newMap = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            newMap[i] = new char[map[i].length * 2];
            for (int j = 0; j < map[i].length; j++) {
                if (isWall(map, j, i)) {
                    newMap[i][j * 2] = '#';
                    newMap[i][j * 2 + 1] = '#';
                } else if (isBox(map, j, i)) {
                    newMap[i][j * 2] = '[';
                    newMap[i][j * 2 + 1] = ']';
                } else if (isRobot(map, j, i)) {
                    newMap[i][j * 2] = '@';
                    newMap[i][j * 2 + 1] = '.';
                } else {
                    newMap[i][j * 2] = '.';
                    newMap[i][j * 2 + 1] = '.';
                }
            }
        }
        return newMap;
    }
}


class Day15InputTransformer implements InputTransformer<Day15Input> {
    @Override
    public Day15Input transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        int index = 0;
        while (!inputLines[index].isEmpty()) {
            index++;
        }
        char[][] map = new char[index][];
        for (int i = 0; i < index; i++) {
            map[i] = inputLines[i].toCharArray();
        }
        String[] directions = new String[inputLines.length - index];
        System.arraycopy(inputLines, index, directions, 0, directions.length);
        return new Day15Input(map, String.join("", directions).chars().mapToObj(operand -> (char) operand).toList());
    }
}


record Position(int x, int y) {
}

record Day15Input(char[][] map, List<Character> movements) {
}