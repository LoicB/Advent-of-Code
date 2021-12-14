package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {
    private final static PolymerisationInstructions SAMPLE = new PolymerisationInstructions(
            "NNCB",
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>("CH", "B"),
                    new AbstractMap.SimpleEntry<>("HH", "N"),
                    new AbstractMap.SimpleEntry<>("CB", "H"),
                    new AbstractMap.SimpleEntry<>("NH", "C"),
                    new AbstractMap.SimpleEntry<>("HB", "C"),
                    new AbstractMap.SimpleEntry<>("HC", "B"),
                    new AbstractMap.SimpleEntry<>("HN", "C"),
                    new AbstractMap.SimpleEntry<>("NN", "C"),
                    new AbstractMap.SimpleEntry<>("BH", "H"),
                    new AbstractMap.SimpleEntry<>("NC", "B"),
                    new AbstractMap.SimpleEntry<>("NB", "B"),
                    new AbstractMap.SimpleEntry<>("BN", "B"),
                    new AbstractMap.SimpleEntry<>("BB", "N"),
                    new AbstractMap.SimpleEntry<>("BC", "B"),
                    new AbstractMap.SimpleEntry<>("CC", "N"),
                    new AbstractMap.SimpleEntry<>("CN", "C")
            )
    );


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 1588)
        );
    }

    private static Stream<Arguments> polymerisationCaseProvider() {
        return Stream.of(
                Arguments.of("NNCB", SAMPLE.rules(), "NCNBCHB"),
                Arguments.of("NCNBCHB", SAMPLE.rules(), "NBCCNBBBCBHCB"),
                Arguments.of("NBCCNBBBCBHCB", SAMPLE.rules(), "NBBBCNCCNBBNBNBBCHBHHBCHB"),
                Arguments.of("NBBBCNCCNBBNBNBBCHBHHBCHB", SAMPLE.rules(), "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 2188189693529L)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(PolymerisationInstructions polymerisationInstructions, int expectedDifference) {
        Day14 day = new Day14();
        long difference = day.partOne(polymerisationInstructions);
        assertEquals(expectedDifference, difference);
    }

    @ParameterizedTest
    @MethodSource("polymerisationCaseProvider")
    void polymerisation(String current, Map<String, String> rules, String expectedResult) {
        Day14 day = new Day14();
        String result = day.polymerisation(current, rules);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(PolymerisationInstructions polymerisationInstructions, long expectedDifference) {
        Day14 day = new Day14();
        long difference = day.partTwo(polymerisationInstructions);
        assertEquals(expectedDifference, difference);
    }
}