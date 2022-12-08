package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    private Cargo prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day5.txt"));
        DayExecutor<Cargo> de = new DayExecutor<>(new Day5InputTransformer());
        return de.transformer().transform(rawInput);
    }

    @Test
    void partOne() throws IOException {
        Cargo cargo = prepareTestParameter();
        var day = new Day5();
        String message = day.partOne(cargo);
        assertEquals("CMZ", message);
    }

    @Test
    void partTwo() throws IOException {
        Cargo cargo = prepareTestParameter();
        var day = new Day5();
        String message = day.partTwo(cargo);
        assertEquals("MCD", message);
    }
}