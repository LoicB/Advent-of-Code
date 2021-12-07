package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14), 37)
        );
    }

    private static Stream<Arguments> getFuelQuantityCaseProvider() {
        return Stream.of(
                Arguments.of(1, 41),
                Arguments.of(2, 37),
                Arguments.of(3, 39),
                Arguments.of(10, 71)
        );
    }

    private static Stream<Arguments> partTwoeCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14), 168)
        );
    }

    private static Stream<Arguments> getFuelQuantityWithProperRateCaseProvider() {
        return Stream.of(
                Arguments.of(5, 168)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<Integer> positions, int expectedFuel) {
        Day7 day = new Day7();
        int fuel = day.partOne(positions);
        assertEquals(expectedFuel, fuel);
    }

    @ParameterizedTest
    @MethodSource("getFuelQuantityCaseProvider")
    void getFuelQuantity(int finalPosition, int expectedFuel) {
        Day7 day = new Day7();
        int fuel = day.getFuelQuantity(List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14), finalPosition);
        assertEquals(expectedFuel, fuel);
    }

    @ParameterizedTest
    @MethodSource("partTwoeCaseProvider")
    void partTwo(List<Integer> positions, int expectedFuel) {
        Day7 day = new Day7();
        int fuel = day.partTwo(positions);
        assertEquals(expectedFuel, fuel);
    }

    @ParameterizedTest
    @MethodSource("getFuelQuantityWithProperRateCaseProvider")
    void getFuelQuantityWithProperRate(int finalPosition, int expectedFuel) {
        Day7 day = new Day7();
        int fuel = day.getFuelQuantityWithProperRate(List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14), finalPosition);
        assertEquals(expectedFuel, fuel);
    }
}