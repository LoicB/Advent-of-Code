package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;

public class Day7 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day7());
    }

    @Override
    public Integer partOne(List<String> input) {
        int splitCount = 0;
        Set<Tachyon> current = Set.of(new Tachyon(input.get(0).length() / 2, 1));
        for (int i = 1; i < input.size() - 1; i++) {
            var newTachyons = current
                    .stream()
                    .map(tachyon -> moveTachyon(input, tachyon))
                    .flatMap(List::stream)
                    .toList();
            splitCount += (newTachyons.size() - current.size());
            current = new HashSet<>(newTachyons);
        }
        return splitCount;
    }

    List<Tachyon> moveTachyon(List<String> manifold, Tachyon tachyon) {
        int x = tachyon.x();
        int y = tachyon.y() + 1;
        if (isEmpty(manifold.get(y).charAt(x))) return List.of(new Tachyon(x, y));
        return List.of(new Tachyon(x - 1, y), new Tachyon(x + 1, y));
    }

    private boolean isEmpty(char c) {
        return c == '.';
    }

    @Override
    public Long partTwo(List<String> input) {
        Map<Tachyon, Long> current = new HashMap<>();
        for (int i = 0; i < input.size() - 1; i++) {
            current = moveTachyons(input, current);
        }
        return current.values().stream().mapToLong(value -> value).sum();
    }


    private Map<Tachyon, Long> moveTachyons(List<String> manifold, Map<Tachyon, Long> currentTachyons) {
        if (currentTachyons.isEmpty()) return Map.of(new Tachyon(manifold.get(0).length() / 2, 1), 1L);
        Map<Tachyon, Long> newTachyonCount = new HashMap<>();
        for (var tachyon : currentTachyons.keySet()) {
            var newTachyons = moveTachyon(manifold, tachyon);
            for (Tachyon newTachyon : newTachyons) {
                var count = newTachyonCount.getOrDefault(newTachyon, 0L);
                newTachyonCount.put(newTachyon, count + currentTachyons.get(tachyon));
            }
        }
        return newTachyonCount;
    }


}

record Tachyon(int x, int y) {
}
