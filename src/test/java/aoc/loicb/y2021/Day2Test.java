package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        new Command("forward", 5),
                        new Command("down", 5),
                        new Command("forward", 8),
                        new Command("up", 3),
                        new Command("down", 8),
                        new Command("forward", 2)
                ), 150)
        );
    }

    private static Stream<Arguments> calculateFinalPositionCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        new Command("forward", 5),
                        new Command("down", 5),
                        new Command("forward", 8),
                        new Command("up", 3),
                        new Command("down", 8),
                        new Command("forward", 2)
                ), new int[]{15, 10})
        );
    }

    private static Stream<Arguments> calculateNextPositionCaseProvider() {
        return Stream.of(
                Arguments.of(new Command("forward", 5), new int[]{0, 0}, new int[]{5, 0}),
                Arguments.of(new Command("down", 5), new int[]{5, 0}, new int[]{5, 5}),
                Arguments.of(new Command("forward", 8), new int[]{5, 5}, new int[]{13, 5}),
                Arguments.of(new Command("up", 3), new int[]{13, 5}, new int[]{13, 2}),
                Arguments.of(new Command("down", 8), new int[]{13, 2}, new int[]{13, 10}),
                Arguments.of(new Command("forward", 2), new int[]{13, 10}, new int[]{15, 10}),

                Arguments.of(new Command("down", 5), new int[]{0, 0}, new int[]{0, 5}),
                Arguments.of(new Command("forward", 8), new int[]{0, 0}, new int[]{8, 0}),
                Arguments.of(new Command("up", 3), new int[]{0, 0}, new int[]{0, -3}),
                Arguments.of(new Command("down", 8), new int[]{0, 0}, new int[]{0, 8}),
                Arguments.of(new Command("forward", 2), new int[]{0, 0}, new int[]{2, 0})

        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        new Command("forward", 5),
                        new Command("down", 5),
                        new Command("forward", 8),
                        new Command("up", 3),
                        new Command("down", 8),
                        new Command("forward", 2)
                ), 900)
        );
    }

    private static Stream<Arguments> calculateFinalPositionPartTwoCaseProvider() {
        return Stream.of(
                Arguments.of(Arrays.asList(
                        new Command("forward", 5),
                        new Command("down", 5),
                        new Command("forward", 8),
                        new Command("up", 3),
                        new Command("down", 8),
                        new Command("forward", 2)
                ), new int[]{15, 60})
        );
    }

    private static Stream<Arguments> calculateNextPositionPartTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new Command("forward", 5), new int[]{0, 0, 0}, new int[]{5, 0, 0}),
                Arguments.of(new Command("down", 5), new int[]{5, 0, 0}, new int[]{5, 0, 5}),
                Arguments.of(new Command("forward", 8), new int[]{5, 0, 5}, new int[]{13, 40, 5}),
                Arguments.of(new Command("up", 3), new int[]{13, 40, 5}, new int[]{13, 40, 2}),
                Arguments.of(new Command("down", 8), new int[]{13, 40, 2}, new int[]{13, 40, 10}),
                Arguments.of(new Command("forward", 2), new int[]{13, 40, 10}, new int[]{15, 60, 10})
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<Command> commands, int expectedProduct) {
        Day2 day = new Day2();
        int product = day.partOne(commands);
        assertEquals(expectedProduct, product);
    }

    @ParameterizedTest
    @MethodSource("calculateFinalPositionCaseProvider")
    void calculatePosition(List<Command> commands, int[] expectedPosition) {
        Day2 day = new Day2();
        int[] finalPosition = day.calculateFinalPositionPartOne(commands, new int[]{0, 0});
        assertEquals(expectedPosition[0], finalPosition[0]);
        assertEquals(expectedPosition[1], finalPosition[1]);
    }

    @ParameterizedTest
    @MethodSource("calculateNextPositionCaseProvider")
    void calculateNextPosition(Command command, int[] currentPosition, int[] expectedPosition) {
        Day2 day = new Day2();
        int[] finalPosition = day.calculateNextPosition(command, currentPosition);
        assertEquals(expectedPosition[0], finalPosition[0]);
        assertEquals(expectedPosition[1], finalPosition[1]);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<Command> commands, int expectedProduct) {
        Day2 day = new Day2();
        long product = day.partTwo(commands);
        assertEquals(expectedProduct, product);
    }

    @ParameterizedTest
    @MethodSource("calculateFinalPositionPartTwoCaseProvider")
    void calculateFinalPositionPart2(List<Command> commands, int[] expectedPosition) {
        Day2 day = new Day2();
        int[] finalPosition = day.calculateFinalPositionPartTwo(commands, new int[]{0, 0, 0});
        assertEquals(expectedPosition[0], finalPosition[0]);
        assertEquals(expectedPosition[1], finalPosition[1]);
    }

    @ParameterizedTest
    @MethodSource("calculateNextPositionPartTwoCaseProvider")
    void calculateNextPositionPartTwo(Command command, int[] currentPosition, int[] expectedPosition) {
        Day2 day = new Day2();
        int[] finalPosition = day.calculateNextPositionPartTWo(command, currentPosition);
        assertEquals(expectedPosition[0], finalPosition[0]);
        assertEquals(expectedPosition[1], finalPosition[1]);
        assertEquals(expectedPosition[2], finalPosition[2]);
    }

}