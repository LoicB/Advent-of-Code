package aoc.loicb.y2021;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {

    private static Stream<Arguments> pixelValueCaseProvider() {
        return Stream.of(
                Arguments.of(new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.'}, 0),
                Arguments.of(new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '#'}, 1),
                Arguments.of(new char[]{'.', '.', '.', '.', '.', '.', '.', '#', '.'}, 2),
                Arguments.of(new char[]{'.', '.', '.', '#', '.', '.', '.', '#', '.'}, 34),
                Arguments.of(new char[]{'#', '.', '.', '.', '.', '.', '.', '.', '.'}, 256),
                Arguments.of(new char[]{'#', '#', '.', '.', '.', '#', '.', '.', '#'}, 393),
                Arguments.of(new char[]{'#', '#', '#', '#', '#', '#', '#', '#', '#'}, 511)

        );
    }

    @Test
    void partOne() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day20.txt"));
        DayExecutor<Day20Input> de = new DayExecutor<>(new InputToImage());
        Day20Input input = de.transformer().transform(rawInput);
        Day20 day = new Day20();
        int score = day.partOne(input);
        assertEquals(35, score);
    }

    @ParameterizedTest
    @MethodSource("pixelValueCaseProvider")
    void pixelValue(char[] neighbourPixels, int expectedValue) {
        Day20 day = new Day20();
        int value = day.pixelValue(neighbourPixels);
        assertEquals(expectedValue, value);
    }

    @Test
    void partTwo() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day20.txt"));
        DayExecutor<Day20Input> de = new DayExecutor<>(new InputToImage());
        Day20Input input = de.transformer().transform(rawInput);
        Day20 day = new Day20();
        int score = day.partTwo(input);
        assertEquals(3351, score);
    }
}