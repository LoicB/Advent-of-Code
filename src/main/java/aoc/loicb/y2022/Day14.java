package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 implements Day<List<List<Position>>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<List<Position>>> de = new DayExecutor<>(new Day14InputTransformer());
        de.execute(new Day14());
    }

    @Override
    public Integer partOne(List<List<Position>> input) {
        Map<Position, Character> map = buildMap(input);
        int initialSize = map.size();
        boolean sandAdded = addSand(map);
        while (sandAdded) {
            sandAdded = addSand(map);
        }
        return map.size() - initialSize;
    }

    private boolean addSand(Map<Position, Character> map) {
        int maxY = map.keySet().stream().mapToInt(Position::y).max().orElseThrow();
        int x = 500;
        int y = 0;
        int currentSize = map.size();
        Position position = new Position(x, y);
        if (map.containsKey(position)) return false;
        while (y <= maxY && currentSize == map.size()) {
            if (!map.containsKey(new Position(x, y + 1))) {
                y++;
            } else if (!map.containsKey(new Position(x - 1, y + 1))) {
                y++;
                x--;
            } else if (!map.containsKey(new Position(x + 1, y + 1))) {
                y++;
                x++;
            } else {
                map.put(position, 'O');
            }
            position = new Position(x, y);

        }
        return currentSize < map.size();


    }

    private void printMap(Map<Position, Character> map) {
        int minX = map.keySet().stream().mapToInt(Position::x).min().orElseThrow();
        int maxX = map.keySet().stream().mapToInt(Position::x).max().orElseThrow();
        int maxY = map.keySet().stream().mapToInt(Position::y).max().orElseThrow();
        for (int j = 0; j <= maxY; j++) {
            for (int i = minX; i <= maxX; i++) {
                Position position = new Position(i, j);
                if (map.containsKey(position)) {
                    System.out.print(map.get(position));
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }


    private Map<Position, Character> buildMap(List<List<Position>> input) {
        Map<Position, Character> map = new HashMap<>();
        input.forEach(positions -> fillMap(positions, map));
        return map;
    }

    private void fillMap(List<Position> input, Map<Position, Character> map) {
        IntStream.range(1, input.size()).forEach(value -> fillMap(input.get(value - 1), input.get(value), map));
    }

    private void fillMap(Position position1, Position position2, Map<Position, Character> map) {
        if (position1.y() == position2.y())
            fillMapX(position1.x(), position2.x(), position1.y(), map);
        if (position1.x() == position2.x())
            fillMapY(position1.y(), position2.y(), position1.x(), map);
    }

    private void fillMapX(int x1, int x2, int y, Map<Position, Character> map) {
        IntStream.range(Math.min(x1, x2), Math.max(x1, x2) + 1).forEach(x -> map.put(new Position(x, y), '#'));
    }

    private void fillMapY(int y1, int y2, int x, Map<Position, Character> map) {
        IntStream.range(Math.min(y1, y2), Math.max(y1, y2) + 1).forEach(y -> map.put(new Position(x, y), '#'));
    }

    @Override
    public Integer partTwo(List<List<Position>> input) {
        Map<Position, Character> map = buildMap(input);
        int maxY = map.keySet().stream().mapToInt(Position::y).max().orElseThrow();
        for (int i = 0; i < 1000; i++) {
            map.put(new Position(i, maxY + 2), '#');
        }
        int initialSize = map.size();
        boolean sandAdded = addSand(map);
        while (sandAdded) {
            sandAdded = addSand(map);
        }
        return map.size() - initialSize;
    }

}

class Day14InputTransformer implements InputTransformer<List<List<Position>>> {
    @Override
    public List<List<Position>> transform(String rawInput) {
        return Arrays.stream(rawInput.split("\\r?\\n"))
                .map(this::transformLine)
                .collect(Collectors.toList());
    }

    private List<Position> transformLine(String line) {
        return Arrays.stream(line.split(" -> "))
                .map(this::transformItem)
                .collect(Collectors.toList());
    }

    private Position transformItem(String item) {
        String[] itemParts = item.split(",");
        return new Position(Integer.parseInt(itemParts[0]), Integer.parseInt(itemParts[1]));
    }


}