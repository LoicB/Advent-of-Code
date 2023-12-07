package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    private static Stream<Arguments> createHandCaseProvider() {
        return Stream.of(
                Arguments.of("32T3K 765", "32T3K", 765),
                Arguments.of("T55J5 684", "T55J5", 684),
                Arguments.of("KK677 28", "KK677", 28),
                Arguments.of("KTJJT 220", "KTJJT", 220),
                Arguments.of("QQQJA 483", "QQQJA", 483)
        );
    }

    private static Stream<Arguments> calculateScoreCaseProvider() {
        return Stream.of(
                Arguments.of("32T3K", 1),
                Arguments.of("T55J5", 3),
                Arguments.of("KK677", 2),
                Arguments.of("KTJJT", 2),
                Arguments.of("QQQJA", 3)
        );
    }

    private static Stream<Arguments> calculateScorePartTwoCaseProvider() {
        return Stream.of(
                Arguments.of("32T3K", 1),
                Arguments.of("T55J5", 5),
                Arguments.of("KK677", 2),
                Arguments.of("KTJJT", 5),
                Arguments.of("QQQJA", 5),
                Arguments.of("J2KA4", 1),
                Arguments.of("J22A4", 3),
                Arguments.of("J2JA4", 3)
        );
    }

    @Test
    void partOne() {
        var input = List.of("32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483");
        var day = new Day7();
        var totalWinnings = day.partOne(input);
        assertEquals(6440, totalWinnings);
    }

    @ParameterizedTest
    @MethodSource("createHandCaseProvider")
    void createHand(String input, String expectedCards, int expectedBet) {
        var day = new Day7();
        var hand = day.createHand(input);
        assertEquals(expectedCards, hand.cards());
        assertEquals(expectedBet, hand.bet());
    }

    @ParameterizedTest
    @MethodSource("calculateScoreCaseProvider")
    void calculateScore(String input, int expectedScore) {
        var day = new Day7();
        var score = day.calculateScore(input);
        assertEquals(expectedScore, score);
    }

    @Test
    void partTwo() {
        var input = List.of("32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483");
        var day = new Day7();
        var totalWinnings = day.partTwo(input);
        assertEquals(5905, totalWinnings);
    }

    @ParameterizedTest
    @MethodSource("calculateScorePartTwoCaseProvider")
    void calculateScorePartTwo(String input, int expectedScore) {
        var day = new Day7();
        var score = day.calculateScorePartTwo(input);
        assertEquals(expectedScore, score);
    }
}