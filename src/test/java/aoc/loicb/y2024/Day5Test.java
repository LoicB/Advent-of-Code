package aoc.loicb.y2024;

import com.github.javaparser.utils.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    List<Pair<Integer, Integer>> rules =
            List.of(
                    new Pair<>(47, 53),
                    new Pair<>(97, 13),
                    new Pair<>(97, 61),
                    new Pair<>(97, 47),
                    new Pair<>(75, 29),
                    new Pair<>(61, 13),
                    new Pair<>(75, 53),
                    new Pair<>(29, 13),
                    new Pair<>(97, 29),
                    new Pair<>(53, 29),
                    new Pair<>(61, 53),
                    new Pair<>(97, 53),
                    new Pair<>(61, 29),
                    new Pair<>(47, 13),
                    new Pair<>(75, 47),
                    new Pair<>(97, 75),
                    new Pair<>(47, 61),
                    new Pair<>(75, 61),
                    new Pair<>(47, 29),
                    new Pair<>(75, 13),
                    new Pair<>(53, 13)
            );
    List<List<Integer>> pages =
            List.of(
                    List.of(75, 47, 61, 53, 29),
                    List.of(97, 61, 53, 29, 13),
                    List.of(75, 29, 13),
                    List.of(75, 97, 47, 61, 53),
                    List.of(61, 13, 29),
                    List.of(97, 13, 75, 29, 47)
            );

    private static Stream<Arguments> isUpdateInRightOrderCaseProvider() {
        return Stream.of(
                Arguments.of(0, true),
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, false),
                Arguments.of(4, false),
                Arguments.of(5, false)
        );
    }

    private static Stream<Arguments> isUpdateInRightOrderSingleCaseProvider() {
        return Stream.of(
                Arguments.of(0, 4, true),
                Arguments.of(0, 14, true),
                Arguments.of(0, 17, true),
                Arguments.of(0, 19, true),
                Arguments.of(0, 0, true),
                Arguments.of(0, 16, true),
                Arguments.of(3, 15, false),
                Arguments.of(4, 7, false)
        );
    }

    private static Stream<Arguments> getMiddlePageNumberCaseProvider() {
        return Stream.of(
                Arguments.of(0, 61),
                Arguments.of(1, 53),
                Arguments.of(2, 29),
                Arguments.of(3, 47),
                Arguments.of(4, 13),
                Arguments.of(5, 75)
        );
    }

    private static Stream<Arguments> fixPageOrderingCaseProvider() {
        return Stream.of(
                Arguments.of(3, List.of(97, 75, 47, 61, 53)),
                Arguments.of(4, List.of(61, 29, 13)),
                Arguments.of(5, List.of(97, 75, 47, 29, 13))
        );
    }

    @Test
    void partOne() {
        var day = new Day5();
        int pageNumbers = day.partOne(new SafetyManual(rules, pages));
        assertEquals(143, pageNumbers);
    }

    @ParameterizedTest
    @MethodSource("isUpdateInRightOrderCaseProvider")
    void isUpdateInRightOrder(int pageIndex, boolean expectedValidity) {
        var day = new Day5();
        var valid = day.isUpdateInRightOrder(pages.get(pageIndex), rules);
        assertEquals(expectedValidity, valid);
    }

    @ParameterizedTest
    @MethodSource("isUpdateInRightOrderSingleCaseProvider")
    void isUpdateInRightOrderSingle(int pageIndex, int ruleIndex, boolean expectedValidity) {
        var day = new Day5();
        var valid = day.isUpdateInRightOrder(pages.get(pageIndex), rules.get(ruleIndex));
        assertEquals(expectedValidity, valid);
    }

    @ParameterizedTest
    @MethodSource("getMiddlePageNumberCaseProvider")
    void getMiddlePageNumber(int pageIndex, int expectedNumber) {
        var day = new Day5();
        var valid = day.getMiddlePageNumber(pages.get(pageIndex));
        assertEquals(expectedNumber, valid);
    }

    @Test
    void partTwo() {
        var day = new Day5();
        int pageNumbers = day.partTwo(new SafetyManual(rules, pages));
        assertEquals(123, pageNumbers);
    }

    @ParameterizedTest
    @MethodSource("fixPageOrderingCaseProvider")
    void fixPageOrdering(int pageIndex, List<Integer> expectedOrder) {
        var day = new Day5();
        var valid = day.fixPageOrdering(pages.get(pageIndex), rules);
        assertEquals(expectedOrder, valid);
    }
}