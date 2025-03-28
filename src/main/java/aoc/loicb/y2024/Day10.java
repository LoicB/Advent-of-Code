package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.function.BiFunction;

public class Day10 implements Day<int[][], Integer> {

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
        de.execute(new Day10());
    }

    @Override
    public Integer partOne(int[][] map) {
        return findTrailheadsAndExplore(map, (x, y) -> explore(map, x, y, createVisitedMap(map)));
    }


    private int findTrailheadsAndExplore(int[][] map, BiFunction<Integer, Integer, Integer> explorer) {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    sum += explorer.apply(j, i);
                }
            }
        }
        return sum;
    }

    private boolean[][] createVisitedMap(int[][] map) {
        boolean[][] visited = new boolean[map.length][];
        for (int i = 0; i < map.length; i++) {
            visited[i] = new boolean[map[i].length];
            for (int j = 0; j < map[i].length; j++) {
                visited[i][j] = false;
            }
        }
        return visited;
    }
    private int explore(int[][] map, int x, int y, boolean[][] visited) {
        visited[y][x] = true;
        if (map[y][x] == 9) return 1;
        int sum = 0;
        if (y > 0 && !visited[y - 1][x] && map[y - 1][x] - map[y][x] == 1) {
            sum += explore(map, x, y - 1, visited);
        }
        if (x > 0 && !visited[y][x - 1] && map[y][x - 1] - map[y][x] == 1) {
            sum += explore(map, x - 1, y, visited);
        }
        if (y < map.length - 1 && !visited[y + 1][x] && map[y + 1][x] - map[y][x] == 1) {
            sum += explore(map, x, y + 1, visited);
        }
        if (x < map[y].length - 1 && !visited[y][x + 1] && map[y][x + 1] - map[y][x] == 1) {
            sum += explore(map, x + 1, y, visited);
        }
        return sum;
    }

    @Override
    public Integer partTwo(int[][] map) {
        return findTrailheadsAndExplore(map, (x, y) -> explore(map, x, y));
    }

    private int explore(int[][] map, int x, int y) {
        if (map[y][x] == 9) return 1;
        int sum = 0;
        if (y > 0 && map[y - 1][x] - map[y][x] == 1) {
            sum += explore(map, x, y - 1);
        }
        if (x > 0 && map[y][x - 1] - map[y][x] == 1) {
            sum += explore(map, x - 1, y);
        }
        if (y < map.length - 1 && map[y + 1][x] - map[y][x] == 1) {
            sum += explore(map, x, y + 1);
        }
        if (x < map[y].length - 1 && map[y][x + 1] - map[y][x] == 1) {
            sum += explore(map, x + 1, y);
        }
        return sum;
    }
}
