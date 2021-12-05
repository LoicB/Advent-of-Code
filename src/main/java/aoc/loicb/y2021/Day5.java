package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 implements Day<List<Line>, Integer> {

    public static void main(String[] args) {
        DayExecutor<List<Line>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Line transformObject(String string) {
                int[] coordinates = Arrays.stream(string.split("[^0-9]+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                return new Line(new Point(coordinates[0], coordinates[1]),
                        new Point(coordinates[2], coordinates[3]));
            }
        });
        de.execute(new Day5());
    }

    @Override
    public Integer partOne(List<Line> linesOfVents) {
        int[][] map = initCoveredMap(linesOfVents);
        List<Point> coveredPoints = linesOfVents.stream().map(this::coveredPoints).collect(ArrayList::new, List::addAll, List::addAll);
        coveredPoints.forEach(point -> map[point.x()][point.y()]++);
        return calculateOverlaps(map);
    }

    protected int[][] initCoveredMap(List<Line> linesOfVents) {
        int maxX = 0;
        int maxY = 0;
        for (Line line : linesOfVents) {
            maxX = Math.max(maxX, line.to().x());
            maxX = Math.max(maxX, line.from().x());
            maxY = Math.max(maxY, line.to().y());
            maxY = Math.max(maxY, line.from().y());
        }
        int[][] map = new int[maxX + 1][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new int[maxY + 1];
            Arrays.fill(map[i], 0);
        }
        return map;
    }

    protected List<Point> coveredPoints(Line line) {
        if (line.to().y() == line.from().y()) return coveredPointsHorizontally(line);
        if (line.to().x() == line.from().x()) return coveredPointsVertically(line);
        return new ArrayList<>();
    }

    private List<Point> coveredPointsHorizontally(Line line) {
        return coveredPoint(line.to().x(), line.from().x(), x -> new Point(x, line.to().y()));
    }

    private List<Point> coveredPointsVertically(Line line) {
        return coveredPoint(line.to().y(), line.from().y(), y -> new Point(line.to().x(), y));
    }

    private List<Point> coveredPoint(int coord1, int coord2, IntFunction<Point> mapper) {
        int from = Math.min(coord1, coord2);
        int to = Math.max(coord1, coord2) + 1;
        return IntStream.range(from, to).mapToObj(mapper).collect(Collectors.toList());
    }

    private int calculateOverlaps(int[][] map) {
        int count = 0;
        for (int[] line : map) {
            for (int point : line) {
                if (point >= 2) count++;
            }
        }
        return count;
    }

    @Override
    public Integer partTwo(List<Line> linesOfVents) {
        int[][] map = initCoveredMap(linesOfVents);
        List<Point> coveredPoints = linesOfVents.stream().map(this::coveredPointsPartTwo).collect(ArrayList::new, List::addAll, List::addAll);
        coveredPoints.forEach(point -> map[point.x()][point.y()]++);
        return calculateOverlaps(map);
    }

    protected List<Point> coveredPointsPartTwo(Line line) {
        if (line.to().y() == line.from().y()) return coveredPointsHorizontally(line);
        if (line.to().x() == line.from().x()) return coveredPointsVertically(line);
        if (isDiagonal(line)) return coveredPointsDiagonal(line);
        return new ArrayList<>();
    }

    private boolean isDiagonal(Line line) {
        return Math.abs(line.to().y() - line.from().y()) == Math.abs(line.to().x() - line.from().x());
    }


    private List<Point> coveredPointsDiagonal(Line line) {
        int directionX = Integer.signum(line.to().x() - line.from().x());
        int directionY = Integer.signum(line.to().y() - line.from().y());

        int x = line.from().x();
        int y = line.from().y();
        int distance = (line.to().x() - line.from().x()) * directionX;
        List<Point> coveredPoints = new ArrayList<>();
        for (int i = 0; i < distance; i++) {
            coveredPoints.add(new Point(x, y));
            x += directionX;
            y += directionY;
        }
        return coveredPoints;
    }

}


record Point(int x, int y) {
}

record Line(Point from, Point to) {
}
