package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    private static Stream<Arguments> countArrangementsCaseProvider() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1),
//                Arguments.of("???.###????.### 1,1,3,1,1,3", 1),
                Arguments.of(".??..??...?##. 1,1,3", 4),
//                Arguments.of(".??..??...?##.?.??..??...?##. 1,1,3,1,1,3", 4),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
//                Arguments.of("?#?#?#?#?#?#?#???#?#?#?#?#?#?#? 1,3,1,6,1,3,1,6", 1),
                Arguments.of("????.#...#... 4,1,1", 1),
//                Arguments.of("????.#...#...?????.#...#... 4,1,1,4,1,1", 1),
                Arguments.of("????. 1", 4),
                Arguments.of("######..#####. 6,5", 1),
                Arguments.of("????.######..#####. 1,6,5", 4),
//                Arguments.of("????.######..#####.?????.######..#####. 1,6,5,1,6,5", 4),
                Arguments.of("?###???????? 3,2,1", 10),
                Arguments.of("?###??????????###???????? 3,2,1,3,2,1", 150),
                Arguments.of("???????##?????#?#? 9,6", 5),
                Arguments.of("?????#???????????#?# 1,4,1,2,1,1", 106)
//                Arguments.of(".###...##..#..###...##.#...###.##.#.....###..##.#..??###???????? 3,2,1,3,2,1,3,2,1,3,2,1,3,2,1", 10)

//
        );
    }

    private static Stream<Arguments> countArrangementsUnfoldCaseProvider() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1),
                Arguments.of(".??..??...?##. 1,1,3", 16384),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                Arguments.of("????.#...#... 4,1,1", 16),
                Arguments.of("????.######..#####. 1,6,5", 2500),
                Arguments.of("?###???????? 3,2,1", 506250),
                Arguments.of("???????##?????#?#? 9,6", 35982),
                Arguments.of("?????#???????????#?# 1,4,1,2,1,1", 80820436091L)


        );
    }

    private static Stream<Arguments> getNextPossibilityCaseProvider() {
        return Stream.of(
                Arguments.of("???.###", 0, 1, 0),
                Arguments.of("???.###", 1, 1, 1),
                Arguments.of("???.###", 2, 1, 2),
                Arguments.of("???.###", 3, 1, -1),
                Arguments.of("???.###", 0, 3, 0),
                Arguments.of("???.###", 0, 4, -1),
                Arguments.of(".??..??...?##.", 0, 1, 1),
                Arguments.of(".??..??...?##.", 1, 1, 1),
                Arguments.of(".??..??...?##.", 2, 1, 2),
                Arguments.of(".??..??...?##.", 3, 1, 5),
                Arguments.of(".??..??...?##.", 0, 3, 10),
                Arguments.of("???.###", 4, 3, 4),
                Arguments.of(".??..??...?##.", 11, 3, -1),
                Arguments.of("?#?#?#?#?#?#?#?", 0, 1, 1)
//                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
//                Arguments.of("????.#...#... 4,1,1", 1),
//                Arguments.of("????.######..#####. 1,6,5", 4),
//                Arguments.of("?###???????? 3,2,1", 10)


        );
    }

    @Test
    void partOne() {
        var input = List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1");
        var day = new Day12();
        var arrangements = day.partOne(input);
        assertEquals(21L, arrangements);
    }

    @ParameterizedTest
    @MethodSource("countArrangementsCaseProvider")
    void countArrangements(String input, int expectedArragements) {
        var day = new Day12();
        var arrangements = day.countArrangements(day.createSpring(input));
        assertEquals(expectedArragements, arrangements);
    }

    @Test
    void partTwo() {
        var input = List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1");
        var day = new Day12();
        var arrangements = day.partTwo(input);
        assertEquals(525152L, arrangements);
    }

    @ParameterizedTest
    @MethodSource("countArrangementsUnfoldCaseProvider")
    void countArrangementsUnfold(String input, long expectedArragements) {
        var day = new Day12();
        var arrangements = day.countArrangementsUnfold(day.createSpring(input));
        assertEquals(expectedArragements, arrangements);
    }

    @ParameterizedTest
    @MethodSource("getNextPossibilityCaseProvider")
    void getNextPossibility(String condition, int from, int consecutiveDamages, int expected) {
        var day = new Day12();
        var possiblity = day.getNextPossibility(condition, from, consecutiveDamages);
        assertEquals(expected, possiblity.orElse(-1));
    }

}