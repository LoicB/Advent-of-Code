package aoc.loicb.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("(())", 0),
                Arguments.of("()()", 0),
                Arguments.of("(((", 3),
                Arguments.of("(()(()(", 3),
                Arguments.of("))(((((", 3),
                Arguments.of("())", -1),
                Arguments.of("))(", -1),
                Arguments.of(")))", -3),
                Arguments.of(")())())", -3)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(")", 1),
                Arguments.of("()())", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String input, int expectedFloor) {
        Day1 day = new Day1();
        int floor = day.partOne(input);
        assertEquals(expectedFloor, floor);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String input, int expectedPosition) {
        Day1 day = new Day1();
        int position = day.partTwo(input);
        assertEquals(expectedPosition, position);
    }
}