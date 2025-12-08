package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.y2025.tools.Range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day5 implements Day<Database, Number> {
    public static void main(String[] args) {
        DayExecutor<Database> de = new DayExecutor<>(rawInput -> {
            var split = rawInput.split("\\r?\\n");
            List<Range> freshIDs = new ArrayList<>();
            int index = 0;
            while (!split[index].isEmpty()) {
                int dash = split[index].indexOf('-');
                var from = Long.parseLong(split[index].substring(0, dash));
                var to = Long.parseLong(split[index].substring(dash + 1));
                freshIDs.add(new Range(from, to));
                index++;
            }
            index++;
            List<Long> ids = new ArrayList<>();
            while (index < split.length) {
                ids.add(Long.parseLong(split[index]));
                index++;
            }
            return new Database(freshIDs, ids);
        });
        de.execute(new Day5());
    }

    @Override
    public Number partOne(Database input) {
        return input
                .IDs()
                .stream()
                .filter(id -> isInRanges(id, input.freshIDs()))
                .count();
    }

    boolean isInRanges(long id, List<Range> freshIDs) {
        for (Range range : freshIDs) {
            if (isInRange(id, range)) return true;
        }
        return false;
    }

    boolean isInRange(long id, Range range) {
        if (id < range.from()) return false;
        return id <= range.to();
    }

    @Override
    public Number partTwo(Database input) {
        List<Range> mergedRanges = new ArrayList<>();
        List<Range> ranges = new ArrayList<>(input.freshIDs());
        ranges.sort(Comparator.comparingLong(Range::from));
        var from = ranges.get(0).from();
        var to = ranges.get(0).to();
        for (Range range : ranges) {
            if (range.from() > to) {
                mergedRanges.add(new Range(from, to));
                from = range.from();
                to = range.to();
            } else {
                to = Math.max(to, range.to());
            }
        }
        mergedRanges.add(new Range(from, to));
        return mergedRanges.stream().mapToLong(this::countFresh).sum();
    }

    private Long countFresh(Range freshID) {
        return freshID.to() - freshID.from() + 1;
    }

}

record Database(List<Range> freshIDs, List<Long> IDs) {
}
