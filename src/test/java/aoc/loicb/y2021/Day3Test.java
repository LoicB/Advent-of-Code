package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
    private final static String[] SAMPLE = new String[]{
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
    };

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 198)
        );
    }

    private static Stream<Arguments> gammaRateCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 22)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 230)
        );
    }

    private static Stream<Arguments> oxygenGeneratorRatingCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 23)
        );
    }

    private static Stream<Arguments> co2ScrubberRatingCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String[] binaries, int expectedPowerConsumption) {
        Day3 day = new Day3();
        int numberOfIncreases = day.partOne(binaries);
        assertEquals(expectedPowerConsumption, numberOfIncreases);
    }

    @ParameterizedTest
    @MethodSource("gammaRateCaseProvider")
    void gammaRate(String[] binaries, int expectedGammaRate) {
        Day3 day = new Day3();
        int numberOfIncreases = day.gammaRate(binaries);
        assertEquals(expectedGammaRate, numberOfIncreases);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String[] binaries, int expectedPowerConsumption) {
        Day3 day = new Day3();
        int numberOfIncreases = day.partTwo(binaries);
        assertEquals(expectedPowerConsumption, numberOfIncreases);
    }

    @ParameterizedTest
    @MethodSource("oxygenGeneratorRatingCaseProvider")
    void oxygenGeneratorRating(String[] binaries, int expectedPowerConsumption) {
        Day3 day = new Day3();
        int numberOfIncreases = day.oxygenGeneratorRating(binaries);
        assertEquals(expectedPowerConsumption, numberOfIncreases);
    }

    @ParameterizedTest
    @MethodSource("co2ScrubberRatingCaseProvider")
    void co2ScrubberRating(String[] binaries, int expectedPowerConsumption) {
        Day3 day = new Day3();
        int numberOfIncreases = day.co2ScrubberRating(binaries);
        assertEquals(expectedPowerConsumption, numberOfIncreases);
    }
}