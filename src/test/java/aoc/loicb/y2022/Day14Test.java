package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {
    private static List<List<Position>> prepareTestParameter() {
        String rawInput = """
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9
                """;
        DayExecutor<List<List<Position>>> de = new DayExecutor<>(new Day14InputTransformer());
        return de.transformer().transform(rawInput);
    }

    @Test
    void partOne() {
        List<List<Position>> input = prepareTestParameter();
        var day = new Day14();
        long inspectedItemsProduct = day.partOne(input);
        assertEquals(24, inspectedItemsProduct);
    }

    @Test
    void partTwo() {
        List<List<Position>> input = prepareTestParameter();
        var day = new Day14();
        long inspectedItemsProduct = day.partTwo(input);
        assertEquals(93, inspectedItemsProduct);
    }
}