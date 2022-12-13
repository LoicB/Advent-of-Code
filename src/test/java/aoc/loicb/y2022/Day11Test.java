package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
    private static Stream<Arguments> executeRoundCaseProvider() {
        return Stream.of(
                Arguments.of(1, new long[]{2, 4, 3, 6}),
                Arguments.of(2, new long[]{6, 10, 3, 10}),
                Arguments.of(3, new long[]{12, 14, 3, 16}),
                Arguments.of(4, new long[]{16, 20, 3, 20}),
                Arguments.of(5, new long[]{22, 24, 4, 26}),
                Arguments.of(6, new long[]{26, 30, 4, 30}),
                Arguments.of(7, new long[]{33, 33, 4, 37}),
                Arguments.of(8, new long[]{36, 40, 4, 40}),
                Arguments.of(9, new long[]{43, 43, 4, 47}),
                Arguments.of(10, new long[]{46, 50, 4, 50}),
                Arguments.of(11, new long[]{54, 52, 5, 58}),
                Arguments.of(12, new long[]{56, 60, 5, 60}),
                Arguments.of(13, new long[]{64, 62, 5, 68}),
                Arguments.of(14, new long[]{67, 69, 5, 71}),
                Arguments.of(15, new long[]{74, 72, 6, 78}),
                Arguments.of(16, new long[]{77, 79, 6, 81}),
                Arguments.of(17, new long[]{84, 82, 7, 88}),
                Arguments.of(18, new long[]{89, 87, 7, 93}),
                Arguments.of(19, new long[]{94, 92, 8, 98}),
                Arguments.of(20, new long[]{99, 97, 8, 103}),
                Arguments.of(1000, new long[]{5204, 4792, 199, 5192})
        );
    }

    private List<Monkey> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day11.txt"));
        DayExecutor<List<Monkey>> de = new DayExecutor<>(new Day11InputTransformer());
        return de.transformer().transform(rawInput);
    }

    @Test
    void partOne() throws IOException {
        List<Monkey> input = prepareTestParameter();
        var day = new Day11();
        long inspectedItemsProduct = day.partOne(input);
        assertEquals(10605, inspectedItemsProduct);
    }

    @Test
    void partTwo() throws IOException {
        List<Monkey> input = prepareTestParameter();
        var day = new Day11();
        long inspectedItemsProduct = day.partTwo(input);
        assertEquals(2713310158L, inspectedItemsProduct);

    }

    @ParameterizedTest
    @MethodSource("executeRoundCaseProvider")
    void executeRound(int numberOfRound, long[] expectedChecks) throws IOException {
        List<Monkey> monkeys = prepareTestParameter();
        var day = new Day11();
        int mod = monkeys.stream().mapToInt(value -> value.modulo).reduce(1, (a, b) -> a * b);
        var numberOfChecks = day.executeRound(monkeys, numberOfRound, aLong -> aLong % mod);
        assertArrayEquals(expectedChecks, numberOfChecks);
    }
}