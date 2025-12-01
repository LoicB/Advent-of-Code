package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private final List<String> input = List.of(
            "L68",
            "L30",
            "R48",
            "L5",
            "R60",
            "L55",
            "L1",
            "L99",
            "R14",
            "L82"
    );

    private static Stream<Arguments> applyRotationCaseProvider() {
        return Stream.of(
                Arguments.of("L68", 50, 82),
                Arguments.of("L30", 82, 52),
                Arguments.of("R48", 52, 0),
                Arguments.of("L5", 0, 95),
                Arguments.of("R60", 95, 55),
                Arguments.of("L55", 55, 0),
                Arguments.of("L1", 0, 99),
                Arguments.of("L99", 99, 0),
                Arguments.of("R14", 0, 14),
                Arguments.of("L82", 14, 32)
        );
    }

    private static Stream<Arguments> convertRotationCaseProvider() {
        return Stream.of(
                Arguments.of("L68", -68),
                Arguments.of("L30", -30),
                Arguments.of("R48", 48),
                Arguments.of("L5", -5),
                Arguments.of("R60", 60),
                Arguments.of("L55", -55),
                Arguments.of("L1", -1),
                Arguments.of("L99", -99),
                Arguments.of("R14", 14),
                Arguments.of("L82", -82)
        );
    }

    @Test
    void partOne() {
        var day = new Day1();
        var password = day.partOne(input);
        assertEquals(3, password);
    }

    @ParameterizedTest
    @MethodSource("applyRotationCaseProvider")
    void applyRotation(String rotation, int current, int expectedPosition) {
        var day = new Day1();
        var position = day.applyRotation(rotation, current);
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("convertRotationCaseProvider")
    void convertRotation(String rotation, int expectedPosition) {
        var day = new Day1();
        var position = day.convertRotation(rotation);
        assertEquals(expectedPosition, position);
    }

    @Test
    void partTwo() {
        var day = new Day1();
        var password = day.partTwo(input);
        assertEquals(6, password);
    }
}