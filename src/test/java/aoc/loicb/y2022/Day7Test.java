package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
    private List<String> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day7.txt"));
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        return de.transformer().transform(rawInput);
    }

    @Test
    void partOne() throws IOException {
        List<String> input = prepareTestParameter();
        var day = new Day7();
        int sumOfSizes = day.partOne(input);
        assertEquals(95437, sumOfSizes);

    }

    @Test
    void partTwo() throws IOException {
        List<String> input = prepareTestParameter();
        var day = new Day7();
        int sumOfSizes = day.partTwo(input);
        assertEquals(24933642, sumOfSizes);
    }
}