package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    private final List<String> input = List.of(
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111"
    );

    private static Stream<Arguments> maximumJoltageCaseProvider() {
        return Stream.of(
                Arguments.of("987654321111111", 98),
                Arguments.of("811111111111119", 89),
                Arguments.of("234234234234278", 78),
                Arguments.of("818181911112111", 92)
        );
    }

    private static Stream<Arguments> maximumJoltageTwelveBatteriesCaseProvider() {
        return Stream.of(
                Arguments.of("987654321111111", 987654321111L),
                Arguments.of("811111111111119", 811111111119L),
                Arguments.of("234234234234278", 434234234278L),
                Arguments.of("818181911112111", 888911112111L)
        );
    }

    @Test
    void partOne() {
        var day = new Day3();
        var totalOutputJoltage = day.partOne(input);
        assertEquals(357, totalOutputJoltage);
    }

    @ParameterizedTest
    @MethodSource("maximumJoltageCaseProvider")
    void maximumJoltage(String bank, int expectedJoltage) {
        var day = new Day3();
        var maximumJoltage = day.maximumJoltage(bank);
        assertEquals(expectedJoltage, maximumJoltage);
    }

    @Test
    void partTwo() {
        var day = new Day3();
        var totalOutputJoltage = day.partTwo(input);
        assertEquals(3121910778619L, totalOutputJoltage);
    }

    @ParameterizedTest
    @MethodSource("maximumJoltageTwelveBatteriesCaseProvider")
    void maximumJoltageTwelveBatteries(String bank, long expectedJoltage) {
        var day = new Day3();
        var maximumJoltage = day.maximumJoltageTwelveBatteries(bank);
        assertEquals(expectedJoltage, maximumJoltage);
    }
}