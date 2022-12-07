package aoc.loicb.y2022;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(List.of("vJrwpWtwJgWrhcsFMMfFFhFp",
                        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                        "PmmdzqPrVvPwwTWBwg",
                        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                        "ttgJtRGJQctTZtZT",
                        "CrZsJsPPZsGzwwsLwLmpwMDw"), 157)
        );
    }

    private static Stream<Arguments> getCompartmentsCaseProvider() {
        return Stream.of(
                Arguments.of("vJrwpWtwJgWrhcsFMMfFFhFp", new String[]{"vJrwpWtwJgWr", "hcsFMMfFFhFp"}),
                Arguments.of("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", new String[]{"jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL"}),
                Arguments.of("PmmdzqPrVvPwwTWBwg", new String[]{"PmmdzqPrV", "vPwwTWBwg"}),
                Arguments.of("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", new String[]{"wMqvLMZHhHMvwLH", "jbvcjnnSBnvTQFn"}),
                Arguments.of("ttgJtRGJQctTZtZT", new String[]{"ttgJtRGJ", "QctTZtZT"}),
                Arguments.of("CrZsJsPPZsGzwwsLwLmpwMDw", new String[]{"CrZsJsPPZsGz", "wwsLwLmpwMDw"})
        );
    }

    private static Stream<Arguments> findCommonItemCaseProvider() {
        return Stream.of(
                Arguments.of("vJrwpWtwJgWr", "hcsFMMfFFhFp", 'p'),
                Arguments.of("jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL", 'L'),
                Arguments.of("PmmdzqPrV", "vPwwTWBwg", 'P'),
                Arguments.of("wMqvLMZHhHMvwLH", "jbvcjnnSBnvTQFn", 'v'),
                Arguments.of("ttgJtRGJ", "QctTZtZT", 't'),
                Arguments.of("CrZsJsPPZsGz", "wwsLwLmpwMDw", 's')
        );
    }

    private static Stream<Arguments> convertToPriorityCaseProvider() {
        return Stream.of(
                Arguments.of('a', 1),
                Arguments.of('A', 27),
                Arguments.of('p', 16),
                Arguments.of('L', 38),
                Arguments.of('P', 42),
                Arguments.of('v', 22),
                Arguments.of('t', 20),
                Arguments.of('s', 19)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(List.of("vJrwpWtwJgWrhcsFMMfFFhFp",
                        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                        "PmmdzqPrVvPwwTWBwg",
                        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                        "ttgJtRGJQctTZtZT",
                        "CrZsJsPPZsGzwwsLwLmpwMDw"), 70)
        );
    }

    private static Stream<Arguments> findRucksacksCommonItemCaseProvider() {
        return Stream.of(
                Arguments.of("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg", 'r'),
                Arguments.of("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw", 'Z')
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<String> input, int expectedSumOfPriorities) {
        var day = new Day3();
        int sumOfPriorities = day.partOne(input);
        assertEquals(expectedSumOfPriorities, sumOfPriorities);
    }

    @ParameterizedTest
    @MethodSource("getCompartmentsCaseProvider")
    void getCompartments(String input, String[] expectedCompartments) {
        var day = new Day3();
        String[] compartments = day.getCompartments(input);
        assertArrayEquals(expectedCompartments, compartments);
    }

    @ParameterizedTest
    @MethodSource("findCommonItemCaseProvider")
    void findCommonItem(String firstCompartment, String secondCompartment, char expectedItem) {
        var day = new Day3();
        char item = day.findCommonItem(firstCompartment, secondCompartment).orElseThrow();
        assertEquals(expectedItem, item);
    }

    @ParameterizedTest
    @MethodSource("convertToPriorityCaseProvider")
    void convertToPriority(char item, int expectedPriority) {
        var day = new Day3();
        var priority = day.convertToPriority(item);
        assertEquals(expectedPriority, priority);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<String> input, int expectedSumOfPriorities) {
        var day = new Day3();
        int sumOfPriorities = day.partTwo(input);
        assertEquals(expectedSumOfPriorities, sumOfPriorities);
    }

    @ParameterizedTest
    @MethodSource("findRucksacksCommonItemCaseProvider")
    void findRucksacksCommonItem(String firstRucksack, String secondRucksack, String thirdRucksack, char expectedItem) {
        var day = new Day3();
        char item = day.findRucksacksCommonItem(firstRucksack, secondRucksack, thirdRucksack).orElseThrow();
        assertEquals(expectedItem, item);
    }
}