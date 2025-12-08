package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day7Test {
    private final List<String> input =
            List.of(
                    ".......S.......",
                    "...............",
                    ".......^.......",
                    "...............",
                    "......^.^......",
                    "...............",
                    ".....^.^.^.....",
                    "...............",
                    "....^.^...^....",
                    "...............",
                    "...^.^...^.^...",
                    "...............",
                    "..^...^.....^..",
                    "...............",
                    ".^.^.^.^.^...^.",
                    "..............."
            );

    private static Stream<Arguments> moveTachyonCaseProvider() {
        return Stream.of(
                Arguments.of(new Tachyon(7, 0), List.of(new Tachyon(7, 1))),
                Arguments.of(new Tachyon(7, 1), List.of(new Tachyon(6, 2), new Tachyon(8, 2)))
        );
    }

    @Test
    void partOne() {
        var day = new Day7();
        var numberOfSplit = day.partOne(input);
        assertEquals(21, numberOfSplit);
    }

    @ParameterizedTest
    @MethodSource("moveTachyonCaseProvider")
    void moveTachyon(Tachyon tachyon, List<Tachyon> expectedTachyons) {
        var day = new Day7();
        var tachyons = day.moveTachyon(input, tachyon);
        assertEquals(expectedTachyons.size(), tachyons.size());
        assertTrue(expectedTachyons.containsAll(tachyons));
    }

    @Test
    void partTwo() {
        var day = new Day7();
        var numberOfSplit = day.partTwo(input);
        assertEquals(40L, numberOfSplit);
    }
}