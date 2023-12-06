package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day6 implements Day<String, Long> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day6());

    }

    @Override
    public Long partOne(String input) {
        String[] line = input.split("\\r?\\n");
        List<Long> times = getNumbers(line[0]);
        List<Long> distances = getNumbers(line[1]);
        return IntStream
                .range(0, times.size()).mapToLong(index -> numberOfWaysBeatRecord(times.get(index), distances.get(index)))
                .reduce(1, (a, b) -> a * b);
    }

    private List<Long> getNumbers(String playedRepresentation) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(playedRepresentation);
        List<Long> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add(Long.parseLong(m.group()));
        }
        return numbers;
    }

    long numberOfWaysBeatRecord(long time, long distance) {
        return LongStream
                .range(0, time)
                .map(hold -> (time - hold) * hold)
                .filter(distanceReached -> distanceReached > distance)
                .count();
    }


    @Override
    public Long partTwo(String input) {
        return partOne(input.replaceAll(" ", ""));
    }
}
