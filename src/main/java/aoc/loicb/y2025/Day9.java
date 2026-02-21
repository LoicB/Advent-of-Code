package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import com.github.javaparser.utils.Pair;

import java.util.*;

public class Day9 implements Day<List<Tile>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<Tile>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Tile transformObject(String string) {
                var split = string.split(",");
                return new Tile(
                        Integer.parseInt(split[0]),
                        Integer.parseInt(split[1])
                );
            }
        });
        de.execute(new Day9());
    }

    @Override
    public Long partOne(List<Tile> input) {
        var largestArea = 0L;
        var smallestArea = 0L;
        for (int i = 0; i < input.size(); i++) {
            for (Tile tile : input) {
                largestArea = Math.max(largestArea, calculateRectangleArea(input.get(i), tile));
                smallestArea = Math.min(smallestArea, calculateRectangleArea(input.get(i), tile));
            }
        }
        return largestArea;
    }

    long calculateRectangleArea(Tile tile1, Tile tile2) {
        return (Math.abs(tile2.x() - tile1.x()) + 1) * (Math.abs(tile2.y() - tile1.y()) + 1);
    }

    @Override
    public Long partTwo(List<Tile> input) {
        var greenTiles = generateGreenTiles(input);
        var greenTilesList = new ArrayList<>(greenTiles);
        var largestArea = 0L;
        var areaToTiles = buildAreaToTiles(input);
        SortedSet<Long> areas = new TreeSet<>(areaToTiles.keySet());
        boolean valid = false;
        while (!valid) {
            var area = areas.last();
            valid = validateTile(areaToTiles.get(area).a, areaToTiles.get(area).b, input, greenTilesList);
            largestArea = area;
            areas.removeLast();
        }
        return largestArea;
    }

    private Map<Long, Pair<Tile, Tile>> buildAreaToTiles(List<Tile> redTiles) {
        Map<Long, Pair<Tile, Tile>> areaToTiles = new HashMap<>();
        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = 0; j < redTiles.size(); j++) {
                if (i != j) {
                    areaToTiles.put(
                            calculateRectangleArea(redTiles.get(i), redTiles.get(j)),
                            new Pair<>(redTiles.get(i), redTiles.get(j))
                    );
                }
            }
        }
        return areaToTiles;
    }

    boolean validateTile(Tile tile1, Tile tile2, Set<Tile> tiles) {
        var minX = Math.min(tile1.x(), tile2.x());
        var minY = Math.min(tile1.y(), tile2.y());
        var maxX = Math.max(tile1.x(), tile2.x());
        var maxY = Math.max(tile1.y(), tile2.y());
        for (Tile tile : tiles) {
            if (tile.x() > minX && tile.x() < maxX && tile.y() > minY && tile.y() < maxY) return false;
        }
        return true;
    }

    boolean validateTile(Tile tile1, Tile tile2, List<Tile> redTiles, List<Tile> greenTiles) {
        var minX = Math.min(tile1.x(), tile2.x());
        var minY = Math.min(tile1.y(), tile2.y());
        var maxX = Math.max(tile1.x(), tile2.x());
        var maxY = Math.max(tile1.y(), tile2.y());
        var containsAngle = contains(new Tile(minX + 1, minY + 1), redTiles)
                && contains(new Tile(minX + 1, maxY - 1), redTiles)
                && contains(new Tile(maxX - 1, minY + 1), redTiles)
                && contains(new Tile(maxX - 1, maxY - 1), redTiles);
        if (!containsAngle) return false;
        for (Tile tile : redTiles) {
            if (tile.x() > minX && tile.x() < maxX && tile.y() > minY && tile.y() < maxY) return false;
        }
        for (Tile tile : greenTiles) {
            if (tile.x() > minX && tile.x() < maxX && tile.y() > minY && tile.y() < maxY) return false;
        }
        return true;
    }


    public boolean contains(Tile test, Tile[] points) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            if ((points[i].y() > test.y()) != (points[j].y() > test.y()) &&
                    (test.x() < (points[j].x() - points[i].x()) * (test.y() - points[i].y()) / (points[j].y() - points[i].y()) + points[i].x())) {
                result = !result;
            }
        }
        return result;
    }

    public boolean contains(Tile test, List<Tile> points) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).y() > test.y()) != (points.get(j).y() > test.y()) &&
                    (test.x() < (points.get(j).x() - points.get(i).x()) * (test.y() - points.get(i).y()) / (points.get(j).y() - points.get(i).y()) + points.get(i).x())) {
                result = !result;
            }
        }
        return result;
    }

    Set<Tile> generateGreenTiles(List<Tile> redTiles) {
        Set<Tile> greenTiles = new HashSet<>();
        for (int i = 0; i < redTiles.size(); i++) {
            var tile1 = redTiles.get(i);
            var tile2 = redTiles.get((i + 1) % redTiles.size());
            greenTiles.addAll(generateGreenTiles(tile1, tile2));
        }
        return greenTiles;
    }

    Set<Tile> generateGreenTiles(Tile tile1, Tile tile2) {
        Set<Tile> greenTiles = new HashSet<>();
        var minY = Math.min(tile1.y(), tile2.y());
        var maxY = Math.max(tile1.y(), tile2.y());
        var minX = Math.min(tile1.x(), tile2.x());
        var maxX = Math.max(tile1.x(), tile2.x());
        for (long i = minY; i <= maxY; i++) {
            for (long j = minX; j <= maxX; j++) {
                greenTiles.add(new Tile(j, i));
            }
        }
        greenTiles.remove(tile1);
        greenTiles.remove(tile2);
        return greenTiles;

    }
}

record Tile(long x, long y) {
}
