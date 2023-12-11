package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 implements Day<List<String>, Number> {

    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day11());

    }

    @Override
    public Integer partOne(List<String> universe) {
        List<String> expandedUniverse = expandUniverse(universe);
        List<Galaxy> galaxies = mapUniverse(expandedUniverse);
        int sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += calculateDistance(galaxies.get(i), galaxies.get(j));
            }
        }
        return sum;
    }

    int calculateDistance(Galaxy galaxy1, Galaxy galaxy2) {
        return Math.abs(galaxy2.x() - galaxy1.x()) + Math.abs(galaxy2.y() - galaxy1.y());
    }

    List<Galaxy> mapUniverse(List<String> universe) {
        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < universe.size(); i++) {
            for (int j = 0; j < universe.get(i).length(); j++) {
                if (isGalaxy(universe.get(i).charAt(j))) galaxies.add(new Galaxy(i, j));
            }
        }
        return galaxies;
    }

    List<String> expandUniverse(List<String> universe) {
        List<String> expandedUniverse = new ArrayList<>(universe);
        List<Integer> horizontalCount = countGalaxiesHorizontally(universe);
        List<Integer> emptyIndexes = IntStream.range(0, horizontalCount.size()).filter(index -> horizontalCount.get(index) == 0).boxed().toList();
        if (!emptyIndexes.isEmpty()) {
            String newLine = buildEmptyLine(universe.get(0).length());
            for (int i = emptyIndexes.size() - 1; i >= 0; i--) {
                expandedUniverse.add(emptyIndexes.get(i), newLine);
            }
        }
        List<Integer> verticalCount = countGalaxiesVertically(universe);
        List<Integer> emptyVerticalIndexes = IntStream.range(0, verticalCount.size()).filter(index -> verticalCount.get(index) == 0).boxed().toList();
        for (int i = emptyVerticalIndexes.size() - 1; i >= 0; i--) {
            final int index = emptyVerticalIndexes.get(i);
            expandedUniverse = expandedUniverse.stream().map(string -> expand(string, index)).collect(Collectors.toList());
        }
        return expandedUniverse;
    }

    public String expand(String line, int position) {
        return line.substring(0, position) + '.' + line.substring(position);
    }

    private String buildEmptyLine(int size) {
        return ".".repeat(Math.max(0, size));
    }

    List<Integer> countGalaxiesHorizontally(List<String> universe) {
        return universe
                .stream()
                .map(this::countLine)
                .collect(Collectors.toList());
    }

    private int countLine(String line) {
        return (int) line
                .chars()
                .filter(this::isGalaxy)
                .count();
    }

    private boolean isGalaxy(int ch) {
        return ch == '#';
    }

    List<Integer> countGalaxiesVertically(List<String> universe) {
        return IntStream
                .range(0, universe.get(0).length())
                .mapToObj(operand -> countRow(universe, operand))
                .collect(Collectors.toList());
    }


    private int countRow(List<String> universe, int index) {
        return (int) universe
                .stream()
                .map(string -> string.charAt(index))
                .filter(this::isGalaxy)
                .count();
    }


    @Override
    public Long partTwo(List<String> universe) {
        return expandingUniverse(universe, 1000000);
    }

    Long expandingUniverse(List<String> universe, int numberOfExpantion) {
        List<String> expandedUniverse = expandUniverse(universe);
        List<Galaxy> expandedGalaxies = mapUniverse(expandedUniverse);
        List<Galaxy> galaxies = mapUniverse(universe);
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                long distance = calculateDistance(galaxies.get(i), galaxies.get(j));
                long expandedDistance = calculateDistance(expandedGalaxies.get(i), expandedGalaxies.get(j));
                sum += distance + (expandedDistance - distance) * (numberOfExpantion - 1);
            }
        }
        return sum;
    }


    record Galaxy(int x, int y) {
    }
}
