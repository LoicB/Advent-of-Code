package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {


    private final List<String> sample =
            List.of("r, wr, b, g, bwu, rb, gb, br",
                    "",
                    "brwrr",
                    "bggr",
                    "gbbr",
                    "rrbgbr",
                    "ubwu",
                    "bwurrg",
                    "brgr",
                    "bbrgwb");

    private static Stream<Arguments> isDesignPossibleCaseProvider() {
        return Stream.of(
                Arguments.of("brwrr", true),
                Arguments.of("bggr", true),
                Arguments.of("gbbr", true),
                Arguments.of("rrbgbr", true),
                Arguments.of("ubwu", false),
                Arguments.of("bwurrg", true),
                Arguments.of("brgr", true),
                Arguments.of("bbrgwb", false)
        );
    }

    private static Stream<Arguments> countPossibleDesignsCaseProvider() {
        return Stream.of(
                Arguments.of("brwrr", 2),
                Arguments.of("bggr", 1),
                Arguments.of("gbbr", 4),
                Arguments.of("rrbgbr", 6),
                Arguments.of("ubwu", 0),
                Arguments.of("bwurrg", 1),
                Arguments.of("brgr", 2),
                Arguments.of("bbrgwb", 0)
        );
    }

    @Test
    void partOne() {
        var day = new Day19();
        var possibleDesigns = day.partOne(sample);
        assertEquals(6, possibleDesigns);
    }

    @ParameterizedTest
    @MethodSource("isDesignPossibleCaseProvider")
    void isDesignPossible(String design, boolean expectedlyPossibility) {
        var day = new Day19();
        var patterns = new ArrayList<>(List.of("r", "wr", "b", "g", "bwu", "rb", "gb", "br"));
        patterns.sort((o1, o2) -> o2.length() - o1.length());
        var possible = day.isDesignPossible(design, patterns);
        assertEquals(expectedlyPossibility, possible);
    }

    @Test
    void partTwo() {
        var day = new Day19();
        var possibleDesigns = day.partTwo(sample);
        assertEquals(16, possibleDesigns);
    }

    @ParameterizedTest
    @MethodSource("countPossibleDesignsCaseProvider")
    void countPossibleDesigns(String design, long expectedlyPossibility) {
        var day = new Day19();
        var patterns = new ArrayList<>(List.of("r", "wr", "b", "g", "bwu", "rb", "gb", "br"));
        patterns.sort((o1, o2) -> o2.length() - o1.length());
        var possible = day.countPossibleDesigns(design, patterns);
        assertEquals(expectedlyPossibility, possible);
    }

}