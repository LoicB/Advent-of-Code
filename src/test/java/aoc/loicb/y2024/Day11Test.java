package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    private static Stream<Arguments> blinkCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(0, 1, 10, 99, 999), List.of(1, 2024, 1, 0, 9, 9, 2021976)),
                Arguments.of(List.of(125, 17), List.of(253000, 1, 7)),
                Arguments.of(List.of(253000, 1, 7), List.of(253, 0, 2024, 14168)),
                Arguments.of(List.of(253, 0, 2024, 14168), List.of(512072, 1, 20, 24, 28676032)),
                Arguments.of(List.of(512072, 1, 20, 24, 28676032), List.of(512, 72, 2024, 2, 0, 2, 4, 2867, 6032)),
                Arguments.of(List.of(512, 72, 2024, 2, 0, 2, 4, 2867, 6032), List.of(1036288, 7, 2, 20, 24, 4048, 1, 4048, 8096, 28, 67, 60, 32)),
                Arguments.of(List.of(1036288, 7, 2, 20, 24, 4048, 1, 4048, 8096, 28, 67, 60, 32), List.of(2097446912, 14168, 4048, 2, 0, 2, 4, 40, 48, 2024, 40, 48, 80, 96, 2, 8, 6, 7, 6, 0, 3, 2))
        );
    }

    private static Stream<Arguments> hasEvenNumberOfDigitsCaseProvider() {
        return Stream.of(
                Arguments.of(1, false),
                Arguments.of(17, true),
                Arguments.of(253000, true),
                Arguments.of(2024, true),
                Arguments.of(14168, false),
                Arguments.of(512072, true)
        );
    }

    @Test
    void partOne() {
        var day = new Day11();
        var numberOfStones1 = day.partOne(List.of(125L));
        assertEquals(19025, numberOfStones1);
        var numberOfStones2 = day.partOne(List.of(17L));
        assertEquals(36287, numberOfStones2);
        var numberOfStones = day.partOne(List.of(125L, 17L));
        assertEquals(55312, numberOfStones);
        assertEquals(55312, 19025 + 36287);
    }

    @ParameterizedTest
    @MethodSource("blinkCaseProvider")
    void blink(List<Integer> stones, List<Integer> expectedNewStones) {
        var day = new Day11();
        var newStones = day.blink(stones.stream().map(integer -> (long) integer).toList());
        assertEquals(expectedNewStones.stream().map(integer -> (long) integer).toList(), newStones);
    }

    @ParameterizedTest
    @MethodSource("hasEvenNumberOfDigitsCaseProvider")
    void hasEvenNumberOfDigits(int stone, boolean expectedEven) {
        var day = new Day11();
        var isEven = day.hasEvenNumberOfDigits(stone);
        assertEquals(expectedEven, isEven);
    }

    @Test
    void partTwo() {
        var day = new Day11();
        var numberOfStones = day.partTwo(List.of(125L, 17L));
        assertEquals(55312, numberOfStones);
    }
}