package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.*;
import java.util.stream.IntStream;

public class Day10 implements Day<String[], Integer> {

    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day10());

    }

    @Override
    public Integer partOne(String[] input) {
        int[][] distances = explore(input);
        int max = 0;
        for (int[] distance : distances) {
            for (int i : distance) {
                max = Math.max(max, i == Integer.MAX_VALUE ? 0 : i);
            }
        }
        return max;
    }

    private static List<Position> getPositions(Position current, String[] input) {
        List<Position> next = new ArrayList<>();
        if (current.x() > 0 && (input[current.x() - 1].charAt(current.y()) == 'F' || input[current.x() - 1].charAt(current.y()) == '7' || input[current.x() - 1].charAt(current.y()) == '|'))
            next.add(new Position(current.x() - 1, current.y()));
        if (input[current.x() + 1].charAt(current.y()) == 'L' || input[current.x() + 1].charAt(current.y()) == 'J' || input[current.x() + 1].charAt(current.y()) == '|')
            next.add(new Position(current.x() + 1, current.y()));
        if (current.y() > 0 && (input[current.x()].charAt(current.y() - 1) == 'F' || input[current.x()].charAt(current.y() - 1) == 'L' || input[current.x()].charAt(current.y() - 1) == '-'))
            next.add(new Position(current.x(), current.y() - 1));
        if (input[current.x()].charAt(current.y() + 1) == '7' || input[current.x()].charAt(current.y() + 1) == 'J' || input[current.x()].charAt(current.y() + 1) == '-')
            next.add(new Position(current.x(), current.y() + 1));
        return next;
    }

    boolean isInIsland(Position position, String[] input) {
        if (position.x() < 0) return false;
        if (position.y() < 0) return false;
        if (position.x() > input.length) return false;
        return position.y() <= input[position.x()].length();
    }

    int[][] explore(String[] input) {
        Position startingPosition = findStartingPoint(input);
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(startingPosition);
        int[][] distances = new int[input.length][];
        for (int i = 0; i < input.length; i++) {
            distances[i] = new int[input[i].length()];
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }
        distances[startingPosition.x()][startingPosition.y()] = 0;
        Set<Position> history = new HashSet<>();

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            history.add(current);
            int distance = distances[current.x()][current.y()];
            List<Position> nextPositions = findNextPositions(current, input[current.x()].charAt(current.y()), input)
                    .stream().filter(position -> isInIsland(position, input))
                    .filter(position -> !history.contains(position))
                    .filter(position -> input[position.x()].charAt(position.y()) != '.')
                    .toList();
            nextPositions.forEach(position -> distances[position.x()][position.y()] = Math.min(distances[position.x()][position.y()], distance + 1));
            queue.addAll(nextPositions);
        }
        return distances;
    }

    List<Position> findNextPositions(Position current, char direction, String[] input) {
        switch (direction) {
            case 'S' -> {
                return getPositions(current, input);
            }
            case 'F' -> {
                return List.of(
                        new Position(current.x() + 1, current.y()),
                        new Position(current.x(), current.y() + 1)
                );
            }
            case 'J' -> {
                return List.of(
                        new Position(current.x() - 1, current.y()),
                        new Position(current.x(), current.y() - 1)
                );
            }
            case 'L' -> {
                return List.of(
                        new Position(current.x() - 1, current.y()),
                        new Position(current.x(), current.y() + 1)
                );
            }
            case '7' -> {
                return List.of(
                        new Position(current.x() + 1, current.y()),
                        new Position(current.x(), current.y() - 1)
                );
            }
            case '|' -> {
                return List.of(
                        new Position(current.x() - 1, current.y()),
                        new Position(current.x() + 1, current.y())
                );
            }
            case '-' -> {
                return List.of(
                        new Position(current.x(), current.y() - 1),
                        new Position(current.x(), current.y() + 1)
                );
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }

    Position findStartingPoint(String[] input) {
        int x = IntStream
                .range(0, input.length)
                .filter(index -> input[index].contains("S"))
                .findFirst().orElse(0);
        return new Position(x, input[x].indexOf('S'));
    }

    @Override
    public Integer partTwo(String[] island) {
        int[][] distances = explore(island);
        String[] newIsland = replaceS(island, distances);
        boolean[][] cycle = findCycle(distances);
        int[][] cycleScore = new int[cycle.length][];
        for (int i = 0; i < cycleScore.length; i++) {
            cycleScore[i] = new int[cycle[i].length];
            Arrays.fill(cycleScore[i], 0);
        }

        for (int i = 0; i < cycle.length; i++) {
            int adding = 0;
            List<Character> currentWall = new ArrayList<>();
            for (int j = 0; j < cycle[i].length; j++) {
                if (cycle[i][j]) {
                    currentWall.add(newIsland[i].charAt(j));
                    if (isCrossIngWall(currentWall, newIsland[i].charAt(j))) {
                        adding = (adding + 1) % 2;
                        currentWall = new ArrayList<>();
                    }
                    if (isNotContinuousWall(currentWall)) {
                        currentWall = new ArrayList<>();
                    }
                } else {
                    cycleScore[i][j] += adding;
                    currentWall = new ArrayList<>();
                }
            }
        }

        int cpt = 0;
        for (int i = 0; i < cycle.length; i++) {
            for (int j = 0; j < cycle[i].length; j++) {
                if (cycleScore[i][j] > 0) {
                    cpt++;
                }
            }
        }
        return cpt;
    }

    private boolean isCrossIngWall(List<Character> currentWall, char current) {
        return current == '|' || (currentWall.contains('7') && currentWall.contains('L') || (currentWall.contains('F') && currentWall.contains('J')));
    }

    private boolean isNotContinuousWall(List<Character> currentWall) {
        return currentWall.stream().filter(character -> character != '|' && character != '-').count() == 2;
    }

    String[] replaceS(String[] island, int[][] distances) {
        String[] newIsland = new String[island.length];
        System.arraycopy(island, 0, newIsland, 0, island.length);
        Position startingPosition = findStartingPoint(island);
        char newC;

        if (startingPosition.x() > 0 && distances[startingPosition.x() - 1][startingPosition.y()] == 1) {
            if (distances[startingPosition.x()][startingPosition.y() - 1] == 1) {
                newC = 'J';
            } else {
                newC = 'L';
            }
        } else {
            if (distances[startingPosition.x()][startingPosition.y() - 1] == 1) {
                newC = '7';
            } else {
                newC = 'F';
            }
        }
        newIsland[startingPosition.x()] = newIsland[startingPosition.x()].replace('S', newC);
        return newIsland;
    }

    boolean[][] findCycle(int[][] distances) {
        Position startingPosition = findMaxPosition(distances);
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(startingPosition);
        boolean[][] cycle = new boolean[distances.length][];
        for (int i = 0; i < distances.length; i++) {
            cycle[i] = new boolean[distances[i].length];
            Arrays.fill(cycle[i], false);
        }

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            cycle[current.x()][current.y()] = true;
            List<Position> nextPositions = getPositions(distances, current);
            queue.addAll(nextPositions);
        }
        return cycle;
    }

    private List<Position> getPositions(int[][] distances, Position current) {
        int distance = distances[current.x()][current.y()];
        List<Position> nextPositions = new ArrayList<>();
        if (current.x() > 0 && distances[current.x() - 1][current.y()] == distance - 1)
            nextPositions.add(new Position(current.x() - 1, current.y()));
        if (current.x() < distances.length - 1 && distances[current.x() + 1][current.y()] == distance - 1)
            nextPositions.add(new Position(current.x() + 1, current.y()));
        if (current.y() > 0 && distances[current.x()][current.y() - 1] == distance - 1)
            nextPositions.add(new Position(current.x(), current.y() - 1));
        if (current.y() < distances[current.x()].length - 1 && distances[current.x()][current.y() + 1] == distance - 1)
            nextPositions.add(new Position(current.x(), current.y() + 1));
        return nextPositions;
    }

    private Position findMaxPosition(int[][] distances) {
        int xMax = 0;
        int yMax = 0;
        int max = 0;
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                if (distances[i][j] < Integer.MAX_VALUE && max < distances[i][j]) {
                    xMax = i;
                    yMax = j;
                    max = distances[i][j];
                }
            }
        }
        return new Position(xMax, yMax);
    }

    record Position(int x, int y) {
    }
}
