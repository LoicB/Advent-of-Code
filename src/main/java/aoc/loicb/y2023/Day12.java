package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day12 implements Day<List<String>, Number> {

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
    public Number partOne(List<String> input) {
        return input
                .stream()
                .map(this::createSpring)
                .mapToLong(this::countArrangements)
                .sum();
    }

    long countAllArrangements(char[] condition, List<Integer> damaged, int index, int damageIndex, int cpt, Map<State, Long> cache) {
        var state = new State(condition, damaged, index, damageIndex, cpt);
        if (cache.containsKey(state)) return cache.get(state);
        if (damageIndex >= damaged.size()) {
            for (int i = index; i < condition.length; i++) {
                if (isDamaged(condition[i])) return 0L;
            }
            return 1L;
        }
        if (index >= condition.length) {
            damageIndex = damageIndex + (cpt == damaged.get(damageIndex) ? 1 : 0);
            if (damageIndex >= damaged.size()) {
                return 1L;
            } else {
                return 0L;
            }
        }
//        if (damageIndex == damaged.size()) return 1L;
        if (cpt > damaged.get(damageIndex)) return 0L;
        long result = 0;
        char current = condition[index];
        // if you can or have to do take it
        if (isOperational(current) || isUnknown(current)) {
            if (cpt == damaged.get(damageIndex)) {
                result += countAllArrangements(condition, damaged, index + 1, damageIndex + 1, 0, cache);
            } else if (cpt == 0) {
                result += countAllArrangements(condition, damaged, index + 1, damageIndex, 0, cache);
            }
        }
        // but if you have too or can, you check it
        if (isDamaged(current) || isUnknown(current)) {
            result += countAllArrangements(condition, damaged, index + 1, damageIndex, cpt + 1, cache);
        }
        cache.put(state, result);
        return result;
    }

    long countArrangements(Spring spring) {
        Map<State, Long> cache = new HashMap<>();
        return countAllArrangements(
                spring.condition().toCharArray(),
                spring.damaged(),
                0,
                0,
                0,
                cache);
    }


    Spring createSpring(String line) {
        String[] arr = line.split(" ");
        return new Spring(arr[0], getNumbers(arr[1]));
    }

    private List<Integer> getNumbers(String input) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        List<Integer> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers;
    }

    private boolean isDamaged(char c) {
        return c == '#';
    }

    private boolean isOperational(char c) {
        return c == '.';
    }

    private boolean isUnknown(char c) {
        return c == '?';
    }

    @Override
    public Number partTwo(List<String> input) {
        return input
                .stream()
                .map(this::createSpring)
                .mapToLong(this::countArrangementsUnfold)
                .sum();
    }

    long countArrangementsUnfold(Spring spring) {
        return countArrangements(unfold(spring));
    }

    Spring unfold(Spring spring) {
        StringBuilder condition = new StringBuilder();
        List<Integer> damaged = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            damaged.addAll(spring.damaged());
            condition.append(spring.condition);
            if (i + 1 < 5) condition.append("?");
        }
        return new Spring(condition.toString(), damaged);
    }

    record Spring(String condition, List<Integer> damaged) {
    }

    record State(char[] condition, List<Integer> damaged, int index, int damageIndex, int cpt) {
    }
}