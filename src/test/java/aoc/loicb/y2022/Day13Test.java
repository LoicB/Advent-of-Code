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

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {
    private static List<PacketPair> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day13.txt"));
        DayExecutor<List<PacketPair>> de = new DayExecutor<>(new Day13InputTransformer());
        return de.transformer().transform(rawInput);
    }

    private static Stream<Arguments> compareCaseProvider() throws IOException {
        List<PacketPair> input = prepareTestParameter();
        return Stream.of(
                Arguments.of(input.get(0), Order.RIGHT),
                Arguments.of(input.get(1), Order.RIGHT),
                Arguments.of(input.get(2), Order.WRONG),
                Arguments.of(input.get(3), Order.RIGHT),
                Arguments.of(input.get(4), Order.WRONG),
                Arguments.of(input.get(5), Order.RIGHT),
                Arguments.of(input.get(6), Order.WRONG),
                Arguments.of(input.get(7), Order.WRONG)
        );
    }

    @Test
    void partOne() throws IOException {
        List<PacketPair> input = prepareTestParameter();
        var day = new Day13();
        long inspectedItemsProduct = day.partOne(input);
        assertEquals(13, inspectedItemsProduct);
    }

    @ParameterizedTest
    @MethodSource("compareCaseProvider")
    void compare(PacketPair packetPair, Order expectedRight) {
        var day = new Day13();

        var rightOrder = day.compare(packetPair);
        assertEquals(expectedRight, rightOrder);
    }

    @Test
    void partTwo() throws IOException {
        List<PacketPair> input = prepareTestParameter();
        var day = new Day13();
        long inspectedItemsProduct = day.partTwo(input);
        assertEquals(140, inspectedItemsProduct);
    }
}