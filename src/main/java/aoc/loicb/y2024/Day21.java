package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 implements Day<List<String>, Long> {

    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day21());

    }

    @Override
    public Long partOne(List<String> input) {
        return input.stream().mapToLong(this::calculateCodeComplexity).sum();
    }

    long calculateCodeComplexity(String code) {
        List<Map<String, Long>> buffer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            buffer.add(i, new HashMap<>());
        }
        var count = findSmallestPathNew(code, Keypad.NumericKeypad(), 0, 3, buffer) - 1;
        var num = code.substring(0, 3);
        return count * Integer.parseInt(num);
    }


    public long findSmallestPathNew(String code, Keypad keypad, int layer, int numberOfRobot, List<Map<String, Long>> buffer) {
        if (buffer.get(layer).containsKey(code)) {
            return buffer.get(layer).get(code);
        }
        var paths = generatePathsNew(code, keypad);
        if (layer + 1 == numberOfRobot) return count(paths);
        var newMultiPah = paths
                .stream()
                .map(multiPath -> multiPath
                        .stream()
                        .map(newCode -> findSmallestPathNew(newCode, Keypad.DirectionalKeypad(), layer + 1, numberOfRobot, buffer)).toList())
                .toList();
        var output = countLengths(newMultiPah);
        buffer.get(layer).put(code, output);
        return output;

    }

    private long count(List<List<String>> paths) {
        return paths
                .stream()
                .map(this::selectSmallestPath)
                .collect(Collectors.joining("A"))
                .length() + 1;
    }

    private String selectSmallestPath(List<String> multiPath) {
        return multiPath
                .stream()
                .min(Comparator.comparingInt(String::length))
                .orElse("");
    }

    private long countLengths(List<List<Long>> paths) {
        return paths.stream().mapToLong(this::selectSmallestPathLengths).sum();
    }

    private Long selectSmallestPathLengths(List<Long> longs) {
        return Collections.min(longs);
    }


    protected List<List<String>> generatePathsNew(String code, Keypad keypad) {
        List<List<String>> paths = new ArrayList<>();
        char current = 'A';
        for (int i = 0; i < code.length(); i++) {
            var multiPath = createPaths(keypad.getButtonPosition(current), keypad.getButtonPosition(code.charAt(i)), keypad)
                    .stream().toList();
            paths.add(multiPath);
            current = code.charAt(i);
        }
        var multiPath = createPaths(keypad.getButtonPosition(current), keypad.getButtonPosition('A'), keypad)
                .stream().toList();
        paths.add(multiPath);
        return paths;
    }

    private int distance(Position from, Position to) {
        return Math.abs(from.x() - to.x()) + Math.abs(from.y() - to.y());

    }

    Set<String> createPaths(Position position1, Position position2, Keypad keypad) {
        return new HashSet<>(createPath(position1, position2, keypad));
    }

    private List<String> createPath(Position from, Position to, Keypad keypad) {
        int distance = distance(from, to);
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(from);
        LinkedList<String> path = new LinkedList<>();
        path.add("");
        List<String> output = new ArrayList<>();
        while (!queue.isEmpty() && !path.isEmpty()) {
            var current = queue.poll();
            var currentPath = path.poll();
            if (current.equals(to)) output.add(currentPath);
            var left = new Position(current.x() - 1, current.y());
            if (currentPath.length() < distance && keypad.isPositionExisting(left)) {
                queue.add(left);
                path.add(currentPath + '<');
            }
            var right = new Position(current.x() + 1, current.y());
            if (currentPath.length() < distance && keypad.isPositionExisting(right)) {
                queue.add(right);
                path.add(currentPath + '>');
            }
            var top = new Position(current.x(), current.y() - 1);
            if (currentPath.length() < distance && keypad.isPositionExisting(top)) {
                queue.add(top);
                path.add(currentPath + '^');
            }
            var down = new Position(current.x(), current.y() + 1);
            if (currentPath.length() < distance && keypad.isPositionExisting(down)) {
                queue.add(down);
                path.add(currentPath + 'v');
            }
        }
        return output;
    }

    @Override
    public Long partTwo(List<String> input) {
        return input.stream().mapToLong(this::calculateCodeComplexityPartTwo).sum();
    }

    long calculateCodeComplexityPartTwo(String code) {
        List<Map<String, Long>> buffer = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            buffer.add(i, new HashMap<>());
        }
        var count = findSmallestPathNew(code, Keypad.NumericKeypad(), 0, 26, buffer) - 1;
        var num = code.substring(0, 3);
        return count * Integer.parseInt(num);
    }

    record Keypad(Map<Character, Position> buttons) {
        static Keypad NumericKeypad() {
            Map<Character, Position> positions = new HashMap<>();
            positions.put('7', new Position(0, 0));
            positions.put('8', new Position(1, 0));
            positions.put('9', new Position(2, 0));
            positions.put('4', new Position(0, 1));
            positions.put('5', new Position(1, 1));
            positions.put('6', new Position(2, 1));
            positions.put('1', new Position(0, 2));
            positions.put('2', new Position(1, 2));
            positions.put('3', new Position(2, 2));
            positions.put('0', new Position(1, 3));
            positions.put('A', new Position(2, 3));
            return new Keypad(positions);
        }

        static Keypad DirectionalKeypad() {
            Map<Character, Position> positions = new HashMap<>();
            positions.put('^', new Position(1, 0));
            positions.put('A', new Position(2, 0));
            positions.put('<', new Position(0, 1));
            positions.put('v', new Position(1, 1));
            positions.put('>', new Position(2, 1));
            return new Keypad(positions);
        }

        Position getButtonPosition(char c) {
            return buttons.get(c);
        }

        boolean isPositionExisting(Position position) {
            return buttons.containsValue(position);
        }

    }
}
