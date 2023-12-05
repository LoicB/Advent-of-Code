package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private static Stream<Arguments> getPlayedNumbersCaseProvider() {
        return Stream.of(
                Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", Set.of(41, 48, 83, 86, 17)),
                Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", Set.of(13, 32, 20, 16, 61)),
                Arguments.of("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", Set.of(1, 21, 53, 59, 44)),
                Arguments.of("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", Set.of(41, 92, 73, 84, 69)),
                Arguments.of("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", Set.of(87, 83, 26, 28, 32)),
                Arguments.of("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", Set.of(31, 18, 13, 56, 72))
        );
    }

    private static Stream<Arguments> getWinningNumbersCaseProvider() {
        return Stream.of(
                Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", Set.of(83, 86, 6, 31, 17, 9, 48, 53)),
                Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", Set.of(61, 30, 68, 82, 17, 32, 24, 19)),
                Arguments.of("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", Set.of(69, 82, 63, 72, 16, 21, 14, 1)),
                Arguments.of("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", Set.of(59, 84, 76, 51, 58, 5, 54, 83)),
                Arguments.of("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", Set.of(88, 30, 70, 12, 93, 22, 82, 36)),
                Arguments.of("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", Set.of(74, 77, 10, 23, 35, 67, 36, 11))
        );
    }

    private static Stream<Arguments> calculatePointsCaseProvider() {
        return Stream.of(
                Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", 8),
                Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", 2),
                Arguments.of("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", 2),
                Arguments.of("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", 1),
                Arguments.of("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", 0),
                Arguments.of("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", 0)
        );
    }

    @Test
    void partOne() {
        var input = List.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");
        var day = new Day4();
        var totalPoint = day.partOne(input);
        assertEquals(13, totalPoint);
    }

    @ParameterizedTest
    @MethodSource("getPlayedNumbersCaseProvider")
    void getPlayedNumbers(String input, Set<Integer> expectedNumbers) {
        var day = new Day4();
        var playedNumbers = day.getPlayedNumbers(input);
        assertEquals(expectedNumbers, playedNumbers);
    }

    @ParameterizedTest
    @MethodSource("getWinningNumbersCaseProvider")
    void getWinningNumbers(String input, Set<Integer> expectedNumbers) {
        var day = new Day4();
        var playedNumbers = day.getWinningNumbers(input);
        assertEquals(expectedNumbers, playedNumbers);
    }

    @ParameterizedTest
    @MethodSource("calculatePointsCaseProvider")
    void calculatePoints(String input, int expectedPoints) {
        var day = new Day4();
        var points = day.calculatePoints(input);
        assertEquals(points, expectedPoints);
    }

    @Test
    void partTwo() {
        var input = List.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");
        var day = new Day4();
        var totalPoint = day.partTwo(input);
        assertEquals(30, totalPoint);
    }
}