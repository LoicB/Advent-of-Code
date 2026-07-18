package aoc.loicb.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("turn on 0,0 through 999,999", 1_000_000),
                Arguments.of("toggle 0,0 through 999,0", 1000),
                Arguments.of("turn off 499,499 through 500,500", 0)
        );
    }

    private static Stream<Arguments> isTurnOnCaseProvider() {
        return Stream.of(
                Arguments.of("turn on 0,0 through 999,999", true),
                Arguments.of("toggle 0,0 through 999,0", false),
                Arguments.of("turn off 499,499 through 500,500", false)
        );
    }

    private static Stream<Arguments> isTurnOffCaseProvider() {
        return Stream.of(
                Arguments.of("turn on 0,0 through 999,999", false),
                Arguments.of("toggle 0,0 through 999,0", false),
                Arguments.of("turn off 499,499 through 500,500", true)
        );
    }

    private static Stream<Arguments> isToggleCaseProvider() {
        return Stream.of(
                Arguments.of("turn on 0,0 through 999,999", false),
                Arguments.of("toggle 0,0 through 999,0", true),
                Arguments.of("turn off 499,499 through 500,500", false)
        );
    }

    private static Stream<Arguments> extractCoordinatesCaseProvider() {
        return Stream.of(
                Arguments.of("turn on 0,0 through 999,999", List.of(0, 0, 999, 999)),
                Arguments.of("toggle 0,0 through 999,0", List.of(0, 0, 999, 0)),
                Arguments.of("turn off 499,499 through 500,500", List.of(499, 499, 500, 500))
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of("turn on 0,0 through 0,0", 1),
                Arguments.of("toggle 0,0 through 999,999", 2_000_000)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String input, int expectedNumberOfLights) {
        var day = new Day6();
        int numberOfLights = day.partOne(List.of(input));
        assertEquals(expectedNumberOfLights, numberOfLights);
    }

    @ParameterizedTest
    @MethodSource("isTurnOnCaseProvider")
    void isTurnOn(String input, boolean expected) {
        var day = new Day6();
        var turnOn = day.isTurnOn(input);
        assertEquals(expected, turnOn);
    }

    @ParameterizedTest
    @MethodSource("isTurnOffCaseProvider")
    void isTurnOff(String input, boolean expected) {
        var day = new Day6();
        var turnOn = day.isTurnOff(input);
        assertEquals(expected, turnOn);
    }

    @ParameterizedTest
    @MethodSource("isToggleCaseProvider")
    void isToggle(String input, boolean expected) {
        var day = new Day6();
        var turnOn = day.isToggle(input);
        assertEquals(expected, turnOn);
    }

    @ParameterizedTest
    @MethodSource("extractCoordinatesCaseProvider")
    void extractCoordinates(String input, List<Integer> expectedCoordinates) {
        var day = new Day6();
        var coordinates = day.extractCoordinates(input);
        assertEquals(expectedCoordinates, coordinates);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String input, int expectedNumberOfLights) {
        var day = new Day6();
        int numberOfLights = day.partTwo(List.of(input));
        assertEquals(expectedNumberOfLights, numberOfLights);
    }
}