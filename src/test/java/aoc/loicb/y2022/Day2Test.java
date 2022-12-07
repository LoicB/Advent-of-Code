package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private static Stream<Arguments> calculateRoundScoreCaseProvider() {
        return Stream.of(
                Arguments.of("A B", 8),
                Arguments.of("B A", 1),
                Arguments.of("C C", 6)
        );
    }

    private static Stream<Arguments> calculateShareScoreCaseProvider() {
        return Stream.of(
                Arguments.of("A A", 1),
                Arguments.of("B A", 1),
                Arguments.of("C A", 1),
                Arguments.of("A B", 2),
                Arguments.of("B B", 2),
                Arguments.of("C B", 2),
                Arguments.of("A C", 3),
                Arguments.of("B C", 3),
                Arguments.of("C C", 3)
        );
    }

    private static Stream<Arguments> calculateRoundOutcomeCaseProvider() {
        return Stream.of(
                Arguments.of("A A", 3),
                Arguments.of("B A", 0),
                Arguments.of("C A", 6),
                Arguments.of("A B", 6),
                Arguments.of("B B", 3),
                Arguments.of("C B", 0),
                Arguments.of("A C", 0),
                Arguments.of("B C", 6),
                Arguments.of("C C", 3)
        );
    }

    @Test
    void partOne() {
        var input = List.of("A Y", "B X", "C Z");
        var day = new Day2();
        int finalScore = day.partOne(input);
        assertEquals(15, finalScore);
    }

    @ParameterizedTest
    @MethodSource("calculateRoundScoreCaseProvider")
    void calculateRoundScore(String input, int expectedScore) {
        var day = new Day2();
        int score = day.calculateRoundScore(input);
        assertEquals(expectedScore, score);
    }

    @ParameterizedTest
    @MethodSource("calculateShareScoreCaseProvider")
    void calculateShareScore(String input, int expectedScore) {
        var day = new Day2();
        int score = day.calculateShareScore(input);
        assertEquals(expectedScore, score);
    }

    @ParameterizedTest
    @MethodSource("calculateRoundOutcomeCaseProvider")
    void calculateRoundOutcome(String input, int expectedScore) {
        var day = new Day2();
        int score = day.calculateRoundOutcome(input);
        assertEquals(expectedScore, score);
    }


    @Test
    void partTwo() {
        var input = List.of("A Y", "B X", "C Z");
        var day = new Day2();
        int finalScore = day.partTwo(input);
        assertEquals(12, finalScore);
    }
}