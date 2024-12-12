package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import com.github.javaparser.utils.Pair;

import java.util.*;

public class Day12 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day12());

    }

    @Override
    public Integer partOne(List<String> map) {
        var history = createHistory(map);
        int price = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (!history[i][j]) {
                    var neighbourhood = findNeighbourhood(map, j, i);
                    neighbourhood.plants().forEach(position -> history[position.y()][position.x()] = true);
                    price += neighbourhood.plants().size() * neighbourhood.fences().size();
                }
            }
        }
        return price;
    }

    private boolean[][] createHistory(List<String> map) {
        boolean[][] history = new boolean[map.size()][];
        for (int i = 0; i < history.length; i++) {
            history[i] = new boolean[map.get(i).length()];
            Arrays.fill(history[i], false);
        }
        return history;
    }

    Pair<Integer, Integer> exploreNeighbourhood(List<String> map, int x, int y) {
        var neighbourhood = findNeighbourhood(map, x, y);
        return new Pair<>(neighbourhood.fences().size(), neighbourhood.plants().size());
    }

    Neighbourhood findNeighbourhood(List<String> map, int x, int y) {
        Set<Position> plants = new HashSet<>();
        List<Position> fences = new ArrayList<>();
        findNeighbourhood(map, new Position(x, y), plants, fences);
        return new Neighbourhood(plants, fences);
    }

    void findNeighbourhood(List<String> map, Position position, Set<Position> plants, List<Position> fences) {
        if (!plants.add(position)) return;
        char plant = map.get(position.y()).charAt(position.x());
        findNeighbours(position.x(), position.y()).forEach(newPosition -> {
            if (isSamePlant(map, plant, newPosition)) {
                findNeighbourhood(map, newPosition, plants, fences);
            } else {
                fences.add(newPosition);
            }
        });
    }


    private boolean isSamePlant(List<String> map, char plant, Position position) {
        return isInside(map, position) && plant == map.get(position.y()).charAt(position.x());
    }

    private boolean isInside(List<String> map, Position position) {
        return position.y() >= 0
                && position.y() < map.size()
                && position.x() >= 0
                && position.x() < map.get(position.y()).length();
    }

    private List<Position> findNeighbours(int x, int y) {
        return List.of(new Position(x, y - 1),
                new Position(x - 1, y),
                new Position(x, y + 1),
                new Position(x + 1, y)
        );
    }


    Neighbourhood findNeighbourhoodPartTwo(List<String> map, int x, int y) {
        Set<Position> plants = new HashSet<>();
        List<Position> fences = new ArrayList<>();
        findNeighbourhoodPartTwo(map, new Position(x, y), plants);
        plants.forEach(position -> findCorners(position, plants, fences));
        return new Neighbourhood(plants, fences);
    }

    void findNeighbourhoodPartTwo(List<String> map, Position position, Set<Position> plants) {
        if (!plants.add(position)) return;
        char plant = map.get(position.y()).charAt(position.x());
        findNeighbours(position.x(), position.y()).forEach(newPosition -> {
            if (isSamePlant(map, plant, newPosition)) {
                findNeighbourhoodPartTwo(map, newPosition, plants);
            }
        });
    }

    void findCorners(Position position, Set<Position> plants, List<Position> fences) {
        var top = new Position(position.x(), position.y() - 1);
        var left = new Position(position.x() - 1, position.y());
        var bottom = new Position(position.x(), position.y() + 1);
        var right = new Position(position.x() + 1, position.y());
        if (plants.contains(top) == plants.contains(right)) {
            var potential = new Position(position.x() + 1, position.y() - 1);
            if (!plants.contains(potential) || !plants.contains(top)) fences.add(potential);
        }
        if (plants.contains(bottom) == plants.contains(right)) {
            var potential = new Position(position.x() + 1, position.y() + 1);
            if (!plants.contains(potential) || !plants.contains(bottom)) fences.add(potential);
        }
        if (plants.contains(top) == plants.contains(left)) {
            var potential = new Position(position.x() - 1, position.y() - 1);
            if (!plants.contains(potential) || !plants.contains(top)) fences.add(potential);
        }
        if (plants.contains(bottom) == plants.contains(left)) {
            var potential = new Position(position.x() - 1, position.y() + 1);
            if (!plants.contains(potential) || !plants.contains(bottom)) fences.add(potential);
        }
    }

    @Override
    public Integer partTwo(List<String> map) {
        var history = createHistory(map);
        int price = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (!history[i][j]) {
                    var neighbourhood = findNeighbourhoodPartTwo(map, j, i);
                    neighbourhood.plants().forEach(position -> history[position.y()][position.x()] = true);
                    price += neighbourhood.plants().size() * neighbourhood.fences().size();
                }
            }
        }
        return price;
    }

    record Neighbourhood(Set<Position> plants, List<Position> fences) {
    }

    record Position(int x, int y) {
    }
}
