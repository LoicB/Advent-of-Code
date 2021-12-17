package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;

public class Day15 implements Day<int[][], Integer> {
    public static void main(String[] args) {
        DayExecutor<int[][]> de = new DayExecutor<>(rawInput -> {
            String[] lines = rawInput.split("\\r?\\n");
            int[][] input = new int[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                input[i] = new int[lines[i].length()];
                for (int j = 0; j < lines[i].length(); j++) {
                    input[i][j] = Character.getNumericValue(lines[i].charAt(j));
                }
            }
            return input;
        });
        de.execute(new Day15());
    }

    @Override
    public Integer partOne(int[][] map) {
        return lowestRiskDijkstra(map);
    }

    protected int lowestRiskDijkstra(int[][] map) {
        boolean[][] visited = new boolean[map.length][];
        for (int i = 0; i < map.length; i++) {
            visited[i] = new boolean[map[i].length];
            Arrays.fill(visited[i], false);
        }
        int[][] risks = new int[map.length][];
        for (int i = 0; i < map.length; i++) {
            risks[i] = new int[map[i].length];
            Arrays.fill(risks[i], Integer.MAX_VALUE);
        }
        risks[0][0] = 0;
        map[0][0] = 0;

        Set<Point> inQueue = new HashSet<>();
        PriorityQueue<Point> neighborsQueue = new PriorityQueue<>(Comparator.comparingInt(o -> risks[o.x()][o.y()]));
        neighborsQueue.add(new Point(0, 0));
        while (!neighborsQueue.isEmpty()) {
            Point current = neighborsQueue.poll();
            List<Point> neighbors = getUnvisitedNeighbors(current, visited);
            neighbors.forEach(point -> risks[point.x()][point.y()] = Math.min(map[point.x()][point.y()] + risks[current.x()][current.y()], risks[point.x()][point.y()]));
            visited[current.x()][current.y()] = true;
            neighborsQueue.addAll(neighbors.stream().filter(point -> !inQueue.contains(point)).toList());
            inQueue.addAll(neighbors);
        }
        return risks[risks.length - 1][risks[risks.length - 1].length - 1];
    }


    private List<Point> getUnvisitedNeighbors(Point current, boolean[][] visited) {
        List<Point> neighbors = new ArrayList<>();
        int x = current.x();
        int y = current.y();
        if (canBeVisitedNeighbors(x, y - 1, visited)) neighbors.add(new Point(x, y - 1));
        if (canBeVisitedNeighbors(x - 1, y, visited)) neighbors.add(new Point(x - 1, y));
        if (canBeVisitedNeighbors(x + 1, y, visited)) neighbors.add(new Point(x + 1, y));
        if (canBeVisitedNeighbors(x, y + 1, visited)) neighbors.add(new Point(x, y + 1));
        return neighbors;
    }

    private boolean canBeVisitedNeighbors(int x, int y, boolean[][] visited) {
        if (x < 0) return false;
        if (y < 0) return false;
        if (x >= visited.length) return false;
        if (y >= visited[x].length) return false;
        return !visited[x][y];
    }

    @Override
    public Integer partTwo(int[][] map) {
        return partOne(duplicateMap(map));
    }

    private int[][] duplicateMap(int[][] map) {
        int[][] newMap = new int[map.length * 5][];
        for (int i = 0; i < 5 * map.length; i++) {
            newMap[i] = new int[map[0].length * 5];
            for (int j = 0; j < 5 * map[0].length; j++) {
                int value = map[i % map.length][j % map[0].length] + i / map.length + j / map[0].length;
                if (value > 9) value -= 9;
                newMap[i][j] = value;
            }
        }
        return newMap;
    }
}
