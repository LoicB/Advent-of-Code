package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 implements Day<List<List<Integer>>, Number> {

    public static void main(String[] args) {
        DayExecutor<List<List<Integer>>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public List<Integer> transformObject(String string) {
                return getNumbers(string);
            }

            private List<Integer> getNumbers(String playedRepresentation) {
                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(playedRepresentation);
                List<Integer> numbers = new ArrayList<>();
                while (m.find()) {
                    numbers.add(Integer.parseInt(m.group()));
                }
                return numbers;
            }
        });
        de.execute(new Day9());

    }

    @Override
    public Number partOne(List<List<Integer>> input) {
        return input
                .stream()
                .mapToInt(this::findNextRightValue)
                .sum();
    }

    int findNextRightValue(List<Integer> history) {
        List<Integer> current = history;
        int nextValue = 0;
        while (keepGoing(current)) {
            nextValue += current.get(current.size() - 1);
            current = calculateDifferences(current);
        }
        return nextValue;
    }

    private boolean keepGoing(List<Integer> history) {
        return history.stream().anyMatch(integer -> integer != 0);
    }


    private List<Integer> calculateDifferences(List<Integer> history) {
        return IntStream
                .range(0, history.size() - 1)
                .mapToObj(ndex -> history.get(ndex + 1) - history.get(ndex))
                .collect(Collectors.toList());
    }

    @Override
    public Number partTwo(List<List<Integer>> input) {
        return input
                .stream()
                .mapToInt(this::findNextLeftValue)
                .sum();
    }


    int findNextLeftValue(List<Integer> history) {
        if (!keepGoing(history)) return 0;
        return history.get(0) - findNextLeftValue(calculateDifferences(history));
    }
}
