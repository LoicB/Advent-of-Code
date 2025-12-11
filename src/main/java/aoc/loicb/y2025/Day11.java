package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 implements Day<Map<String, List<String>>, Number> {
    public static void main(String[] args) {
        DayExecutor<Map<String, List<String>>> de = new DayExecutor<>(Day11::transformInput);
        de.execute(new Day11());
    }

    static Map<String, List<String>> transformInput(String rawInput) {
        Map<String, List<String>> input = new HashMap<>();
        var lines = rawInput.split("\\r?\\n");
        for (String line : lines) {
            var split = line.split(":");
            input.put(split[0], List.of(split[1].trim().split(" ")));
        }
        return input;

    }

    @Override
    public Long partOne(Map<String, List<String>> input) {
        return partOne(input, "you", "out", new HashMap<>());
    }

    private long partOne(Map<String, List<String>> input, String from, String to) {
        return partOne(input, from, to, new HashMap<>());
    }

    private long partOne(Map<String, List<String>> input, String from, String to, Map<String, Long> buffer) {
        if (from.equals(to)) return 1L;
        if (buffer.containsKey(from)) return buffer.get(from);
        long count = 0L;
        for (String output : input.getOrDefault(from, new ArrayList<>())) {
            count += partOne(input, output, to, buffer);
        }
        buffer.put(from, count);
        return count;
    }

    @Override
    public Long partTwo(Map<String, List<String>> input) {
        return partOne(input, "svr", "dac") * partOne(input, "dac", "fft") * partOne(input, "fft", "out")
                + partOne(input, "svr", "fft") * partOne(input, "fft", "dac") * partOne(input, "dac", "out");
    }
}
