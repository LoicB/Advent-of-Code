package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {

    private static List<Valve> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day16.txt"));
        DayExecutor<List<Valve>> de = new DayExecutor<>(new Day16InputTransformer());
        return de.transformer().transform(rawInput);
    }

    @Test
    void partOne() throws IOException {
        List<Valve> valves = prepareTestParameter();
        var day = new Day16();
        long inspectedItemsProduct = day.partOne(valves);
        assertEquals(1651, inspectedItemsProduct);
    }


    @Test
    void partTwo() throws IOException {
        List<Valve> valves = prepareTestParameter();
        var day = new Day16();
        long inspectedItemsProduct = day.partTwo(valves);
        assertEquals(1707, inspectedItemsProduct);
    }
}