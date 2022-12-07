package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
    private List<Optional<Integer>> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day1.txt"));
        DayExecutor<List<Optional<Integer>>> de = new DayExecutor<>(new Day1InputTransformer());
        return de.transformer().transform(rawInput);
    }

    @Test
    void partOne() throws IOException {
        List<Optional<Integer>> calories = prepareTestParameter();
        Day1 day = new Day1();
        int mostCalories = day.partOne(calories);
        assertEquals(24000, mostCalories);
    }

    @Test
    void countElves() throws IOException {
        List<Optional<Integer>> calories = prepareTestParameter();
        Day1 day = new Day1();
        int countElves = day.countElves(calories);
        assertEquals(5, countElves);
    }

    @Test
    void partTwo() throws IOException {
        List<Optional<Integer>> calories = prepareTestParameter();
        Day1 day = new Day1();
        int mostCalories = day.partTwo(calories);
        assertEquals(45000, mostCalories);
    }
}