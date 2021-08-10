package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import com.google.common.base.Objects;

import java.util.HashSet;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 implements Day<String, Integer> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day3());
    }

    @Override
    public Integer partOne(String input) {
        return getVisitedHouse(input).size();
    }

    private Set<House> getVisitedHouse(String path) {
        Set<House> houses = new HashSet<>();
        int x = 0;
        int y = 0;
        houses.add(new House(x, y));
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            switch (c) {
                case '<' -> x--;
                case '>' -> x++;
                case 'v' -> y--;
                case '^' -> y++;
            }
            houses.add(new House(x, y));
        }
        return houses;
    }

    @Override
    public Integer partTwo(String input) {
        String santaPath = filterPath(input, n -> n % 2 == 0);
        String robotSantaPath = filterPath(input, n -> n % 2 == 1);
        Set<House> visitedHouse = getVisitedHouse(santaPath);
        visitedHouse.addAll(getVisitedHouse(robotSantaPath));
        return visitedHouse.size();
    }

    private String filterPath(String path, IntPredicate predicate) {
        return IntStream.range(0, path.length()).filter(predicate).map(path::charAt).mapToObj(Character::toString).collect(Collectors.joining());
    }
}

record House(int x, int y) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return x == house.x && y == house.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }
}
