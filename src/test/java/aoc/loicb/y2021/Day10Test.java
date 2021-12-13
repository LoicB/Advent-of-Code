package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]{
                        "[({(<(())[]>[[{[]{<()<>>",
                        "[(()[<>])]({[<{<<[]>>(",
                        "{([(<{}[<>[]}>{[]{[(<()>",
                        "(((({<>}<{<{<>}{[]{[]{}",
                        "[[<[([]))<([[{}[[()]]]",
                        "[{[{({}]{}}([{[{{{}}([]",
                        "{<[[]]>}<{[{[{[]{()[[[]",
                        "[<(<(<(<{}))><([]([]()",
                        "<{([([[(<>()){}]>(<<{{",
                        "<{([{{}}[<[[[<>{}]]]>[]]"
                }, 26397)
        );
    }

    private static Stream<Arguments> foundInvalidCharacterCaseProvider() {
        return Stream.of(
                Arguments.of("{([(<{}[<>[]}>{[]{[(<()>", '}'),
                Arguments.of("[[<[([]))<([[{}[[()]]]", ')'),
                Arguments.of("[{[{({}]{}}([{[{{{}}([]", ']'),
                Arguments.of("[<(<(<(<{}))><([]([]()", ')'),
                Arguments.of("<{([([[(<>()){}]>(<<{{", '>'),
                Arguments.of("[({(<(())[]>[[{[]{<()<>>", null)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]{
                        "[({(<(())[]>[[{[]{<()<>>",
                        "[(()[<>])]({[<{<<[]>>(",
                        "{([(<{}[<>[]}>{[]{[(<()>",
                        "(((({<>}<{<{<>}{[]{[]{}",
                        "[[<[([]))<([[{}[[()]]]",
                        "[{[{({}]{}}([{[{{{}}([]",
                        "{<[[]]>}<{[{[{[]{()[[[]",
                        "[<(<(<(<{}))><([]([]()",
                        "<{([([[(<>()){}]>(<<{{",
                        "<{([{{}}[<[[[<>{}]]]>[]]"
                }, 288957)
        );
    }

    private static Stream<Arguments> getRemainingCharCaseProvider() {
        return Stream.of(
                Arguments.of("[({(<(())[]>[[{[]{<()<>>", "}}]])})]"),
                Arguments.of("[(()[<>])]({[<{<<[]>>(", ")}>]})"),
                Arguments.of("(((({<>}<{<{<>}{[]{[]{}", "}}>}>))))"),
                Arguments.of("{<[[]]>}<{[{[{[]{()[[[]", "]]}}]}]}>"),
                Arguments.of("<{([{{}}[<[[[<>{}]]]>[]]", "])}>")
        );
    }

    private static Stream<Arguments> calculateScoreRemainingCaseProvider() {
        return Stream.of(
                Arguments.of("}}]])})]", 288957),
                Arguments.of(")}>]})", 5566),
                Arguments.of("}}>}>))))", 1480781),
                Arguments.of("]]}}]}]}>", 995444),
                Arguments.of("])}>", 294)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String[] navigationSubsystem, int expectedScore) {
        Day10 day = new Day10();
        int score = day.partOne(navigationSubsystem);
        assertEquals(expectedScore, score);
    }

    @ParameterizedTest
    @MethodSource("foundInvalidCharacterCaseProvider")
    void foundInvalidCharacter(String line, Character expectedInvalid) {
        Day10 day = new Day10();
        Optional<Character> invalid = day.foundInvalidCharacter(line);
        assertEquals(Optional.ofNullable(expectedInvalid), invalid);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String[] navigationSubsystem, long expectedScore) {
        Day10 day = new Day10();
        long score = day.partTwo(navigationSubsystem);
        assertEquals(expectedScore, score);
    }

    @ParameterizedTest
    @MethodSource("getRemainingCharCaseProvider")
    void getRemainingChar(String line, String expectedRemaining) {
        Day10 day = new Day10();
        List<Character> remaining = day.getRemainingChar(line);
        assertEquals(expectedRemaining, remaining.stream().map(character -> Character.toString(character)).collect(Collectors.joining("")));
    }

    @ParameterizedTest
    @MethodSource("calculateScoreRemainingCaseProvider")
    void calculateScoreRemaining(String remaining, long expectedScore) {
        Day10 day = new Day10();
        long score = day.calculateScoreRemaining(remaining.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList()));
        assertEquals(expectedScore, score);
    }
}