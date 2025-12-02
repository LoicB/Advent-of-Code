package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private final List<Range> input = List.of(
            new Range(11, 22),
            new Range(95, 115),
            new Range(998, 1012),
            new Range(1188511880, 1188511890),
            new Range(222220, 222224),
            new Range(1698522, 1698528),
            new Range(446443, 446449),
            new Range(38593856, 38593862),
            new Range(565653, 565659),
            new Range(824824821, 824824827),
            new Range(2121212118, 2121212124)
    );

    private static Stream<Arguments> getInvalidIdsFromRangeCaseProvider() {
        return Stream.of(
                Arguments.of(0, List.of(11L, 22L)),
                Arguments.of(1, List.of(99L)),
                Arguments.of(2, List.of(1010L)),
                Arguments.of(3, List.of(1188511885L)),
                Arguments.of(4, List.of(222222L)),
                Arguments.of(5, List.of()),
                Arguments.of(6, List.of(446446L)),
                Arguments.of(7, List.of(38593859L)),
                Arguments.of(8, List.of()),
                Arguments.of(9, List.of()),
                Arguments.of(10, List.of())
        );
    }

    private static Stream<Arguments> isIdValidCaseProvider() {
        return Stream.of(
                Arguments.of(55, false),
                Arguments.of(6464, false),
                Arguments.of(6264, true),
                Arguments.of(123123, false),
                Arguments.of(123124, true),
                Arguments.of(5, true),
                Arguments.of(6463, true),
                Arguments.of(1000000, true)
        );
    }

    private static Stream<Arguments> getInvalidIdsFromRangePartTwoCaseProvider() {
        return Stream.of(
                Arguments.of(0, List.of(11L, 22L)),
                Arguments.of(1, List.of(99L, 111L)),
                Arguments.of(2, List.of(999L, 1010L)),
                Arguments.of(3, List.of(1188511885L)),
                Arguments.of(4, List.of(222222L)),
                Arguments.of(5, List.of()),
                Arguments.of(6, List.of(446446L)),
                Arguments.of(7, List.of(38593859L)),
                Arguments.of(8, List.of(565656L)),
                Arguments.of(9, List.of(824824824L)),
                Arguments.of(10, List.of(2121212121L))
        );
    }

    private static Stream<Arguments> isIdValidPartTwoCaseProvider() {
        return Stream.of(
                Arguments.of(55, false),
                Arguments.of(6464, false),
                Arguments.of(6264, true),
                Arguments.of(123123, false),
                Arguments.of(123124, true),
                Arguments.of(5, true),
                Arguments.of(6463, true),
                Arguments.of(1000000, true),
                Arguments.of(12341234, false),
                Arguments.of(123123123, false),
                Arguments.of(1212121212, false),
                Arguments.of(1111111, false),
                Arguments.of(111, false),
                Arguments.of(999, false),
                Arguments.of(565656, false),
                Arguments.of(824824824, false),
                Arguments.of(2121212121, false),
                Arguments.of(446446, false),
                Arguments.of(6161561615L, false),
                Arguments.of(6161661616L, false),
                Arguments.of(65856585, false),
                Arguments.of(64146414, false),
                Arguments.of(64246424, false),
                Arguments.of(2323202, true)
        );
    }

    @Test
    void partOne() {
        var day = new Day2();
        var invalidIDs = day.partOne(input);
        assertEquals(1227775554, invalidIDs);
    }

    @ParameterizedTest
    @MethodSource("getInvalidIdsFromRangeCaseProvider")
    void getInvalidIdsFromRange(int rangeIndex, List<Long> expectedInvalids) {
        var day = new Day2();
        var invalids = day.getInvalidIdsFromRange(input.get(rangeIndex));
        assertEquals(expectedInvalids, invalids);
    }

    @ParameterizedTest
    @MethodSource("isIdValidCaseProvider")
    void isIdValid(long id, boolean expectedValidity) {
        var day = new Day2();
        var isValid = day.isIdValid(id);
        assertEquals(expectedValidity, isValid);
    }

    @Test
    void partTwo() {
        var day = new Day2();
        var invalidIDs = day.partTwo(input);
        assertEquals(4174379265L, invalidIDs);
    }

    @ParameterizedTest
    @MethodSource("getInvalidIdsFromRangePartTwoCaseProvider")
    void getInvalidIdsFromRangePartTwo(int rangeIndex, List<Long> expectedInvalids) {
        var day = new Day2();
        var invalids = day.getInvalidIdsFromRangePartTwo(input.get(rangeIndex));
        assertEquals(expectedInvalids, invalids);
    }

    @ParameterizedTest
    @MethodSource("isIdValidPartTwoCaseProvider")
    void isIdValidPartTwo(long id, boolean expectedValidity) {
        var day = new Day2();
        var isValid = day.isIdValidPartTwo(id);
        assertEquals(expectedValidity, isValid);
    }
}