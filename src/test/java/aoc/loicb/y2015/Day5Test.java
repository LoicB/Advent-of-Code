package aoc.loicb.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("ugknbfddgicrmopn", 1),
                Arguments.of("aaa", 1),
                Arguments.of("jchzalrnumimnmhp", 0),
                Arguments.of("haegwjzuvuyypxyu", 0),
                Arguments.of("dvszwmarrgswjxmb", 0)
        );
    }

    private static Stream<Arguments> isNiceCaseProvider() {
        return Stream.of(
                Arguments.of("ugknbfddgicrmopn", true),
                Arguments.of("aaa", true),
                Arguments.of("jchzalrnumimnmhp", false),
                Arguments.of("haegwjzuvuyypxyu", false),
                Arguments.of("dvszwmarrgswjxmb", false)
        );
    }

    private static Stream<Arguments> asThreeVowelsProvider() {
        return Stream.of(
                Arguments.of("ugknbfddgicrmopn", true),
                Arguments.of("aaa", true),
                Arguments.of("jchzalrnumimnmhp", true),
                Arguments.of("haegwjzuvuyypxyu", true),
                Arguments.of("dvszwmarrgswjxmb", false)
        );
    }

    private static Stream<Arguments> hasLetterTwiceCaseProvider() {
        return Stream.of(
                Arguments.of("ugknbfddgicrmopn", true),
                Arguments.of("aaa", true),
                Arguments.of("jchzalrnumimnmhp", false),
                Arguments.of("haegwjzuvuyypxyu", true),
                Arguments.of("dvszwmarrgswjxmb", true)
        );
    }

    private static Stream<Arguments> doesNotContainCaseProvider() {
        return Stream.of(
                Arguments.of("ugknbfddgicrmopn", true),
                Arguments.of("aaa", true),
                Arguments.of("jchzalrnumimnmhp", true),
                Arguments.of("haegwjzuvuyypxyu", false),
                Arguments.of("dvszwmarrgswjxmb", true)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of("qjhvhtzxzqqjkmpb", 1),
                Arguments.of("xxyxx", 1),
                Arguments.of("uurcxstgmygtbstg", 0),
                Arguments.of("ieodomkazucvgmuy", 0)
        );
    }

    private static Stream<Arguments> isNicePart2CaseProvider() {
        return Stream.of(
                Arguments.of("qjhvhtzxzqqjkmpb", true),
                Arguments.of("xxyxx", true),
                Arguments.of("uurcxstgmygtbstg", false),
                Arguments.of("ieodomkazucvgmuy", false)
        );
    }

    private static Stream<Arguments> hasPairNoOverlapCaseProvider() {
        return Stream.of(
                Arguments.of("xyxy", true),
                Arguments.of("aabcdefgaa", true),
                Arguments.of("aaa", false)
        );
    }

    private static Stream<Arguments> hasLetterTwiceWithOneBetweenCaseProvider() {
        return Stream.of(
                Arguments.of("xyx", true),
                Arguments.of("abcdefeghi", true),
                Arguments.of("aaa", true)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String input, int expectedNumberOfString) {
        Day5 day = new Day5();
        int numberOfNiceStrings = day.partOne(new String[]{input});
        assertEquals(expectedNumberOfString, numberOfNiceStrings);
    }

    @ParameterizedTest
    @MethodSource("isNiceCaseProvider")
    void isNicePart1(String input, boolean expectedNice) {
        Day5 day = new Day5();
        boolean nice = day.isNicePart1(input);
        assertEquals(expectedNice, nice);
    }

    @ParameterizedTest
    @MethodSource("asThreeVowelsProvider")
    void hasThreeVowels(String input, boolean expectedThreeVowel) {
        Day5 day = new Day5();
        boolean hasThreeVowelsResult = day.hasThreeVowels(input);
        assertEquals(expectedThreeVowel, hasThreeVowelsResult);
    }

    @ParameterizedTest
    @MethodSource("hasLetterTwiceCaseProvider")
    void hasLetterTwice(String input, boolean expectedLetterTwice) {
        Day5 day = new Day5();
        boolean hasLetterTwiceResult = day.hasLetterTwice(input);
        assertEquals(expectedLetterTwice, hasLetterTwiceResult);
    }

    @ParameterizedTest
    @MethodSource("doesNotContainCaseProvider")
    void doesNotContain(String input, boolean expectedDoesNotContain) {
        Day5 day = new Day5();
        boolean doesNotContainResult = day.doesNotContain(input);
        assertEquals(expectedDoesNotContain, doesNotContainResult);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String input, int expectedNumberOfString) {
        Day5 day = new Day5();
        int numberOfNiceStrings = day.partTwo(new String[]{input});
        assertEquals(expectedNumberOfString, numberOfNiceStrings);
    }

    @ParameterizedTest
    @MethodSource("isNicePart2CaseProvider")
    void isNicePart2(String input, boolean expectedNice) {
        Day5 day = new Day5();
        boolean nice = day.isNicePart2(input);
        assertEquals(expectedNice, nice);
    }

    @ParameterizedTest
    @MethodSource("hasPairNoOverlapCaseProvider")
    void hasPairNoOverlap(String input, boolean expectedHasPair) {
        Day5 day = new Day5();
        boolean pair = day.hasPairNoOverlap(input);
        assertEquals(expectedHasPair, pair);
    }

    @ParameterizedTest
    @MethodSource("hasLetterTwiceWithOneBetweenCaseProvider")
    void hasLetterTwiceWithOneBetween(String input, boolean expectedLetterTwice) {
        Day5 day = new Day5();
        boolean letterTwice = day.hasLetterTwiceWithOneBetween(input);
        assertEquals(expectedLetterTwice, letterTwice);
    }

}