package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 5934)
        );
    }

    private static Stream<Arguments> numberOfFishAfterCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 1, 5),
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 2, 6),
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 10, 12),
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 18, 26),
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 80, 5934)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(3, 4, 3, 1, 2), 26984457539L)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<Integer> fish, int expectedNumberOfFish) {
        Day6 day = new Day6();
        long numberOfFish = day.partOne(fish);
        assertEquals(expectedNumberOfFish, numberOfFish);
    }

    @ParameterizedTest
    @MethodSource("numberOfFishAfterCaseProvider")
    void numberOfFishAfter(List<Integer> fish, int numberOfDays, int expectedNumberOfFish) {
        Day6 day = new Day6();
        long numberOfFish = day.numberOfFishAfter(fish, numberOfDays);
        assertEquals(expectedNumberOfFish, numberOfFish);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<Integer> fish, long expectedNumberOfFish) {
        Day6 day = new Day6();
        long numberOfFish = day.partTwo(fish);
        assertEquals(expectedNumberOfFish, numberOfFish);
    }
}