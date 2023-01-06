package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {
    private static List<Reading> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day15.txt"));
        DayExecutor<List<Reading>> de = new DayExecutor<>(new Day15InputTransformer());
        return de.transformer().transform(rawInput);
    }


    @Test
    void partOne() throws IOException {
        List<Reading> input = prepareTestParameter();
        var day = new Day15();
        long inspectedItemsProduct = day.partOne(input);
        assertEquals(26, inspectedItemsProduct);
    }

    @Test
    void partTwo() throws IOException {
        List<Reading> input = prepareTestParameter();
        var day = new Day15();
        long inspectedItemsProduct = day.partTwo(input);
        assertEquals(56000011, inspectedItemsProduct);
    }
}