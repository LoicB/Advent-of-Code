package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import aoc.loicb.y2025.tools.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;

public class Day2 implements Day<List<Range>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<Range>> de = new DayExecutor<>(new InputToObjectList<>(",") {
            @Override
            public Range transformObject(String string) {
                var split = string.indexOf('-');
                var from = Long.parseLong(string.substring(0, split));
                var to = Long.parseLong(string.substring(split + 1));
                return new Range(from, to);
            }
        });
        de.execute(new Day2());
    }

    @Override
    public Long partOne(List<Range> ranges) {
        return ranges
                .stream()
                .map(this::getInvalidIdsFromRange)
                .flatMap(List::stream)
                .mapToLong(Long::longValue)
                .sum();
    }

    List<Long> getInvalidIdsFromRange(Range range) {
        return LongStream
                .range(range.from(), range.to() + 1)
                .filter(id -> !isIdValid(id))
                .boxed()
                .toList();
    }

    boolean isIdValid(long id) {
        var digits = numberToDigits(id);
        var numberOfDigits = digits.size();
        if (numberOfDigits % 2 == 1) return true;
        var halfIndex = numberOfDigits / 2;
        for (int i = 0; i < halfIndex; i++) {
            if (!Objects.equals(digits.get(i), digits.get(i + halfIndex))) return true;
        }
        return false;
    }

    private List<Long> numberToDigits(long id) {
        var number = id;
        List<Long> digits = new ArrayList<>();
        while (number > 0) {
            digits.add(number % 10);
            number = number / 10;
        }
        return digits;
    }

    @Override
    public Long partTwo(List<Range> ranges) {
        return ranges
                .stream()
                .map(this::getInvalidIdsFromRangePartTwo)
                .flatMap(List::stream)
                .mapToLong(Long::longValue)
                .sum();
    }


    List<Long> getInvalidIdsFromRangePartTwo(Range range) {
        return LongStream
                .range(range.from(), range.to() + 1)
                .filter(id -> !isIdValidPartTwo(id))
                .boxed()
                .toList();
    }

    boolean isIdValidPartTwo(long id) {
        if (id < 10) return true;
        var digits = numberToDigits(id);
        var tortoise = 0;
        var hare = 1;
        while (hare < digits.size()) {
            if (Objects.equals(digits.get(tortoise), digits.get(hare))) {
                if (isCyclingWithGap(digits, tortoise, hare)) return false;
            }
            tortoise++;
            hare += 2;
        }
        return true;
    }

    private boolean isCyclingWithGap(List<Long> digits, int tortoise, int hare) {
        for (int i = 1; i < digits.size(); i++) {
            var currentTortoise = (tortoise + i) % digits.size();
            var currentHare = (hare + i) % digits.size();
            if (!Objects.equals(digits.get(currentTortoise), digits.get(currentHare))) return false;
        }
        return true;
    }
}
