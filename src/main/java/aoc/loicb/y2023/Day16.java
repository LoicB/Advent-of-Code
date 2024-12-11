package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;
import java.util.stream.Collectors;

enum Direction {
    Up('^', -1, 0),
    Right('>', 0, 1),
    Down('v', 1, 0),
    Left('<', 0, -1);

    final char icon;
    final int x;
    final int y;

    Direction(char icon, int x, int y) {
        this.icon = icon;
        this.x = x;
        this.y = y;
    }

    int x() {
        return x;
    }

    int y() {
        return y;
    }
}

public class Day16 implements Day<List<char[]>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<char[]>> de = new DayExecutor<>(rawInput ->
                Arrays
                        .stream(rawInput.split("\\r?\\n"))
                        .map(String::toCharArray)
                        .collect(Collectors.toList()));
        de.execute(new Day16());
    }

    @Override
    public Number partOne(List<char[]> contraption) {
        Light startingLight = new Light(0, 0, Direction.Right);
        return goThrough(startingLight, contraption);
    }

    private int goThrough(Light startingLight, List<char[]> contraption) {
        Set<Light> history = new HashSet<>();
        history.add(startingLight);
        LinkedList<Light> queue = new LinkedList<>();
        queue.add(startingLight);
        while (!queue.isEmpty()) {
            Light current = queue.poll();
            queue.addAll(moveLight(current, contraption)
                    .stream()
                    .filter(history::add)
                    .toList());
        }
        return history.stream().map(Light::position).collect(Collectors.toSet()).size();
    }

    List<Light> moveLight(Light light, List<char[]> contraption) {
        List<Direction> directions = getDirection(light, contraption);
        Position position = light.position();
        return directions
                .stream()
                .map(direction -> new Light(move(position, direction), direction))
                .filter(light1 -> isLightInContraption(light1, contraption))
                .map(light1 -> applyLight(light1, contraption))
                .toList();
    }

    private Light applyLight(Light light, List<char[]> contraption) {
        if (contraption.get(light.position().x())[light.position().y()] == '.')
            contraption.get(light.position().x())[light.position().y()] = light.direction().icon;
        return light;
    }

    private boolean isLightInContraption(Light light, List<char[]> contraption) {
        return light.position().x() >= 0
                && light.position().x() < contraption.size()
                && light.position().y() >= 0
                && light.position().y() < contraption.get(light.position().x()).length;
    }

    Position move(Position currentPosition, Direction direction) {
        return new Position(currentPosition.x() + direction.x(), currentPosition.y() + direction.y());
    }

    List<Direction> getDirection(Light light, List<char[]> contraption) {
        Position position = light.position();
        if (contraption.get(position.x())[position.y()] == '|') {
            if (light.direction() == Direction.Right || light.direction() == Direction.Left)
                return List.of(Direction.Down, Direction.Up);
            return List.of(light.direction());
        }
        if (contraption.get(position.x())[position.y()] == '-') {
            if (light.direction() == Direction.Up || light.direction() == Direction.Down)
                return List.of(Direction.Left, Direction.Right);
            return List.of(light.direction());
        }
        if (contraption.get(position.x())[position.y()] == '\\') {
            if (light.direction() == Direction.Up) return List.of(Direction.Left);
            if (light.direction() == Direction.Right) return List.of(Direction.Down);
            if (light.direction() == Direction.Down) return List.of(Direction.Right);
            return List.of(Direction.Up);
        }
        if (contraption.get(position.x())[position.y()] == '/') {
            if (light.direction() == Direction.Up) return List.of(Direction.Right);
            if (light.direction() == Direction.Right) return List.of(Direction.Up);
            if (light.direction() == Direction.Down) return List.of(Direction.Left);
            return List.of(Direction.Down);
        }
        return List.of(light.direction());
    }

    @Override
    public Number partTwo(List<char[]> contraption) {
        int max = 0;
        for (int i = 0; i < contraption.size(); i++) {
            Light light1 = new Light(0, i, Direction.Right);
            max = Math.max(max, goThrough(light1, copy(contraption)));
            Light light2 = new Light(contraption.get(i).length - 1, i, Direction.Left);
            max = Math.max(max, goThrough(light2, copy(contraption)));
        }
        for (int i = 0; i < contraption.getFirst().length; i++) {
            Light light1 = new Light(i, 0, Direction.Down);
            max = Math.max(max, goThrough(light1, copy(contraption)));
            Light light2 = new Light(i, contraption.size() - 1, Direction.Up);
            max = Math.max(max, goThrough(light2, copy(contraption)));
        }
        return max;
    }

    private List<char[]> copy(List<char[]> contraption) {
        List<char[]> copy = new ArrayList<>();
        for (char[] chars : contraption) {
            char[] cc = new char[chars.length];
            System.arraycopy(chars, 0, cc, 0, chars.length);
            copy.add(cc);
        }
        return copy;
    }

    record Position(int x, int y) {
    }

    record Light(Position position, Direction direction) {
        Light(int x, int y, Direction direction) {
            this(new Position(x, y), direction);
        }
    }

}