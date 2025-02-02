package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 implements Day<List<String>, Integer> {
    Map<Status, String> buffer = new HashMap<>();
    Map<String, List<String>[]> buffer2 = new HashMap<>();

    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day21());

    }

    // <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A
    // <v<AA>A>^AAvA<^A>AAvA^Av<A>^A<A>Av<A>^A<A>Av<A<A>>^AAvA<^A>A
    @Override
    public Integer partOne(List<String> input) {
        return input.stream().mapToInt(this::calculateCodeComplexity).sum();
    }

    int calculateCodeComplexity(String code) {
        // generate buffer
        System.out.println(findSmallestPath("<", Keypad.DirectionalKeypad(), 0, 2));
        System.out.println(findSmallestPath("<", Keypad.DirectionalKeypad(), 0, 24));
        System.out.println(code);
        System.out.println(findSmallestPath(code, Keypad.NumericKeypad(), 0, 3));
//        var path = findTotalPathNew(code, Keypad.NumericKeypad(), Keypad.DirectionalKeypad(), 0, 2);
        var path = findSmallestPath(code, Keypad.NumericKeypad(), 0, 3);
        var num = code.substring(0, 3);
        return path.length() * Integer.parseInt(num);
    }

    private String findTotalPathNew(String code, Keypad numericKeypad, Keypad directionalKeypad, int level, int numberOfRobot) {
        var status = new Status(level, code);
        if (buffer.containsKey(status)) return buffer.get(status);
        if (level == numberOfRobot) {
            char current = 'A';
            var output = new StringBuilder();
            for (int i = 0; i < code.length(); i++) {
                var last = createPath2(directionalKeypad.getButtonPosition(current), directionalKeypad.getButtonPosition(code.charAt(i)), directionalKeypad);
                var min = last.stream().mapToInt(String::length).min().orElse(0);
                var buttons = last.stream().filter(s -> s.length() == min).findFirst().orElse("");
                output.append(buttons);
                current = code.charAt(i);
            }
            return output.toString();
        }
        char current = 'A';
        var output = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            var keyboard = level == 0 ? numericKeypad : directionalKeypad;
            var first = createPath2(keyboard.getButtonPosition(current), keyboard.getButtonPosition(code.charAt(i)), keyboard);
            var second = first.stream().map(s -> findTotalPathNew(s, numericKeypad, directionalKeypad, level + 1, numberOfRobot)).toList();
            var min = second.stream().mapToInt(String::length).min().orElse(0);
            var buttons = second.stream().filter(s -> s.length() == min).findFirst().orElse("");
            output.append(buttons);
            current = code.charAt(i);

        }
        buffer.put(status, output.toString());
        return output.toString();
    }

    public String findSmallestPath(String code, Keypad keypad, int layer, int numberOfRobot) {
//        var status = new Status(layer, code);
//        if (buffer.containsKey(status)) return buffer.get(status);
        if (layer + 1 == numberOfRobot) {
            return findPaths(code, keypad).stream()
                    .min(Comparator.comparingInt(String::length))
                    .orElse("");
        }
        var newPaths = generatePaths(code, keypad);
//        buffer2.put(code, newPaths);
        var output = Arrays
                .stream(newPaths)
                .map(strings -> strings
                        .stream()
                        .map(s -> findSmallestPath(s, Keypad.DirectionalKeypad(), layer + 1, numberOfRobot))
                        .min(Comparator.comparingInt(String::length))
                        .orElse(""))
                .collect(Collectors.joining());
//        buffer.put(status, output);
        return output;
    }

    public String findSmallestPath(String code) {
        System.out.println(code);
        var current = findPaths(code, Keypad.NumericKeypad());
        for (int i = 0; i < 2; i++) {
            System.out.println(current.size());
            System.out.println(current);
            var directionPaths = current
                    .stream()
                    .map(this::findDirectionalPaths)
                    .flatMap(List::stream)
                    .distinct()
                    .toList();
            var minSize = directionPaths.stream().mapToInt(String::length).min().orElse(0);
            current = directionPaths.stream().filter(s -> s.length() == minSize).toList();
        }
        return current.stream()
                .min(Comparator.comparingInt(String::length))
                .orElse("");

    }


    protected List<String> findDirectionalPaths(String code) {
//        System.out.println(code);
        return findPaths(code, Keypad.DirectionalKeypad());
    }

    protected List<String> findPaths(String code, Keypad keypad) {
        List<String>[] paths = generatePaths(code, keypad);
//        System.out.println(Arrays.toString(paths));
        return buildPath(paths);
    }

    protected List<String>[] generatePaths(String code, Keypad keypad) {
        List<String>[] paths = new List[code.length()];
        char current = 'A';
        for (int i = 0; i < code.length(); i++) {
            paths[i] = createPaths(keypad.getButtonPosition(current), keypad.getButtonPosition(code.charAt(i)), keypad)
                    .stream().map(s -> s + 'A').toList();
            current = code.charAt(i);
        }
        return paths;
    }

    private List<String> buildPath(List<String>[] paths) {
        List<String> output = new ArrayList<>();
        for (List<String> path : paths) {
            List<String> newBuilder = new ArrayList<>();
            for (String s : path) {
                if (output.isEmpty()) {
                    newBuilder.add(s);
                }
                for (String sb : output) {
                    newBuilder.add(sb + s);
                }
            }
            output = newBuilder;
        }
        return output;
    }

    private String findPath(String code, Keypad keypad) {
        char current = 'A';
        var output = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            output.append(createPath(keypad.getButtonPosition(current), keypad.getButtonPosition(code.charAt(i)), keypad));
            output.append('A');
            current = code.charAt(i);
        }
        return output.toString();
    }

    String createPath(Position from, Position to, Keypad keypad) {
        int distance = distance(from, to);
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(from);
//        LinkedList<String> path = new LinkedList<>();
        Map<Position, String> paths = new HashMap<>();
//        path.add("");
        paths.put(from, "");
        while (!queue.isEmpty()) {
            var current = queue.poll();
            var currentPath = paths.get(current);
            var left = new Position(current.x() - 1, current.y());
            if (distance(to, current) > distance(to, left) && keypad.isPositionExisting(left)) {
                queue.add(left);
                paths.put(left, currentPath + '<');
                continue;
            }
            var down = new Position(current.x(), current.y() + 1);
            if (distance(to, current) > distance(to, down) && keypad.isPositionExisting(down)) {
                queue.add(down);
                paths.put(down, currentPath + 'v');
                continue;
            }
            var right = new Position(current.x() + 1, current.y());
            if (distance(to, current) > distance(to, right) && keypad.isPositionExisting(right)) {
                queue.add(right);
                paths.put(right, currentPath + '>');
                continue;
            }
            var top = new Position(current.x(), current.y() - 1);
            if (distance(to, current) > distance(to, top) && keypad.isPositionExisting(top)) {
                queue.add(top);
                paths.put(top, currentPath + '^');
            }
//            var left = new Position(current.x() - 1, current.y());
//            evaluateNextStep(current, left, '<', queue, paths, keypad);
//            var right = new Position(current.x() + 1, current.y());
//            evaluateNextStep(current, right, '>', queue, paths, keypad);
//            var top = new Position(current.x(), current.y() - 1);
//            evaluateNextStep(current, top, '^', queue, paths, keypad);
//            var down = new Position(current.x(), current.y() + 1);
//            evaluateNextStep(current, down, 'v', queue, paths, keypad);
        }
        var path = paths.get(to);
//        return path;
        char[] charArray = path.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    List<String> createPath2(Position from, Position to, Keypad keypad) {
        int distance = distance(from, to);
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(from);
        LinkedList<String> path = new LinkedList<>();
        Map<Position, String> paths = new HashMap<>();
        path.add("");
        paths.put(from, "");
        List<String> output = new ArrayList<>();
        while (!queue.isEmpty()) {
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
//            evaluateNextStep(current, left, '<', queue, paths, keypad);
//
//            evaluateNextStep(current, right, '>', queue, paths, keypad);
//            var top = new Position(current.x(), current.y() - 1);
//            evaluateNextStep(current, top, '^', queue, paths, keypad);
//            var down = new Position(current.x(), current.y() + 1);
//            evaluateNextStep(current, down, 'v', queue, paths, keypad);
        }
        return output.stream().map(s -> s + "A").toList();
    }

    private int distance(Position from, Position to) {
        return Math.abs(from.x() - to.x()) + Math.abs(from.y() - to.y());

    }

    private void evaluateNextStep(Position current, Position next, char movement, LinkedList<Position> queue, Map<Position, String> paths, Keypad keypad) {
        var currentPath = paths.get(current);
//        if (next.equals(new Position(0,3))) {
//            System.out.println("WHY???");
//            System.out.println(current+" "+next+" "+movement);
//            System.out.println(keypad.isPositionExisting(next));
//        }
        if (keypad.isPositionExisting(next)
                && (!paths.containsKey(next) || paths.get(next).length() > currentPath.length() + 1)
        ) {
            queue.add(next);
            paths.put(next, currentPath + movement);
        }
    }

    Set<String> createPaths(Position position1, Position position2, Keypad keypad) {
        return new HashSet<>(createPath3(position1, position2, keypad));
//        var path = createPath(position1, position2);
//        Set<String> permutations = new HashSet<>();
//        findPathPermutations(path, "", permutations);
//        return permutations;
    }

    List<String> createPath3(Position from, Position to, Keypad keypad) {
        int distance = distance(from, to);
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(from);
        LinkedList<String> path = new LinkedList<>();
        Map<Position, String> paths = new HashMap<>();
        path.add("");
        paths.put(from, "");
        List<String> output = new ArrayList<>();
        while (!queue.isEmpty()) {
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
//            evaluateNextStep(current, left, '<', queue, paths, keypad);
//
//            evaluateNextStep(current, right, '>', queue, paths, keypad);
//            var top = new Position(current.x(), current.y() - 1);
//            evaluateNextStep(current, top, '^', queue, paths, keypad);
//            var down = new Position(current.x(), current.y() + 1);
//            evaluateNextStep(current, down, 'v', queue, paths, keypad);
        }
        return output;
    }


    private void findPathPermutations(String path, String curr, Set<String> permutations) {
        if (path.length() == 0) {
            permutations.add(curr);
            return;
        }
        for (int i = 0; i < path.length(); i++) {
            char ch = path.charAt(i);
            String ros = path.substring(0, i) +
                    path.substring(i + 1);
            findPathPermutations(ros, curr + ch, permutations);
        }
    }

    String createPath(Position position1, Position position2) {
        String output = "<".repeat(Math.max(0, position1.x() - position2.x())) +
                ">".repeat(Math.max(0, position2.x() - position1.x())) +
                "^".repeat(Math.max(0, position1.y() - position2.y())) +
                "v".repeat(Math.max(0, position2.y() - position1.y()));
        return output;
    }

    @Override
    public Integer partTwo(List<String> input) {
        return input.stream().mapToInt(this::calculateCodeComplexityPartTwo).sum();
    }

    int calculateCodeComplexityPartTwo(String code) {
        System.out.println(code);
//        var path = findTotalPathNew(code, Keypad.NumericKeypad(), Keypad.DirectionalKeypad(), 0, 26);
        var path = findSmallestPath(code, Keypad.NumericKeypad(), 0, 26);
        System.out.println(path);
        var num = code.substring(0, 3);
        return path.length() * Integer.parseInt(num);
    }

    record Status(int level, String code) {
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
