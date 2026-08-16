package aoc.loicb.y2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    private final List<String> input = List.of(
            "\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\""
    );

    private static Stream<Arguments> coundCharactersCaseProvider() {
        return Stream.of(
                Arguments.of("\"\"", 0),
                Arguments.of("\"abc\"", 3),
                Arguments.of("\"aaa\\\"aaa\"", 7),
                Arguments.of("\"\\x27\"", 1)
        );
    }

    private static Stream<Arguments> escapeCountCaseProvider() {
        return Stream.of(
                Arguments.of("\"\"", 6),
                Arguments.of("\"abc\"", 9),
                Arguments.of("\"aaa\\\"aaa\"", 16),
                Arguments.of("\"\\x27\"", 11)
        );
    }

    private static Stream<Arguments> escapeCaseProvider() {
        return Stream.of(
                Arguments.of("\"\"", "\"\\\"\\\"\""),
                Arguments.of("\"abc\"", "\"\\\"abc\\\"\""),
                Arguments.of("\"aaa\\\"aaa\"", "\"\\\"aaa\\\\\\\"aaa\\\"\""),
                Arguments.of("\"\\x27\"", "\"\\\"\\\\x27\\\"\"")
        );
    }

    @Test
    void partOne() {
        var day = new Day8();
        var space = day.partOne(input);
        assertEquals(12, space);
    }

    @ParameterizedTest
    @MethodSource("coundCharactersCaseProvider")
    void coundCharacters(String input, int expectedNumberOfCharacters) {
        var day = new Day8();
        int numberOfCharacters = day.coundCharacters(input);
        assertEquals(expectedNumberOfCharacters, numberOfCharacters);
    }

    @Test
    void partTwo() {
        var day = new Day8();
        var space = day.partTwo(input);
        assertEquals(19, space);
    }

    @ParameterizedTest
    @MethodSource("escapeCountCaseProvider")
    void escapeCount(String input, int expectedNumberOfCharacters) {
        var day = new Day8();
        int numberOfCharacters = day.escapeCount(input);
        assertEquals(expectedNumberOfCharacters, numberOfCharacters);
    }

    @ParameterizedTest
    @MethodSource("escapeCaseProvider")
    void escape(String input, String expectedEscaped) {
        var day = new Day8();
        var escaped = day.escape(input);
        assertEquals(expectedEscaped, escaped);
    }
}