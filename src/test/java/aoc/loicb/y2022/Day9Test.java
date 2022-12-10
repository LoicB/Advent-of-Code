package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    private static Stream<Arguments> modeTailCaseProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1), new Position(0, 0), new Position(0, 0)),
                Arguments.of(new Position(0, 2), new Position(0, 0), new Position(0, 1)),
                Arguments.of(new Position(0, 3), new Position(0, 1), new Position(0, 2)),
                Arguments.of(new Position(0, 4), new Position(0, 2), new Position(0, 3)),
                Arguments.of(new Position(1, 4), new Position(0, 3), new Position(0, 3)),
                Arguments.of(new Position(2, 4), new Position(0, 3), new Position(1, 4)),
                Arguments.of(new Position(3, 4), new Position(1, 4), new Position(2, 4)),
                Arguments.of(new Position(4, 4), new Position(2, 4), new Position(3, 4)),
                Arguments.of(new Position(4, 3), new Position(3, 4), new Position(3, 4)),
                Arguments.of(new Position(4, 2), new Position(3, 4), new Position(4, 3)),
                Arguments.of(new Position(4, 1), new Position(4, 3), new Position(4, 2)),
                Arguments.of(new Position(3, 1), new Position(4, 2), new Position(4, 2)),
                Arguments.of(new Position(2, 1), new Position(4, 2), new Position(3, 1))
        );
    }

    private List<Motion> prepareInput() {
        return List.of(
                new Motion('R', 4),
                new Motion('U', 4),
                new Motion('L', 3),
                new Motion('D', 1),
                new Motion('R', 4),
                new Motion('D', 1),
                new Motion('L', 5),
                new Motion('R', 2)
        );
    }

    @Test
    void partOne() {
        var day = new Day9();
        var input = prepareInput();
        var numberOfPositions = day.partOne(input);
        assertEquals(13, numberOfPositions);
    }

    @ParameterizedTest
    @MethodSource("modeTailCaseProvider")
    void modeTail(Position head, Position tail, Position expectedNewHead) {
        var day = new Day9();
        var newHead = day.moveTail(tail, head);
        assertEquals(expectedNewHead, newHead);
    }

    @Test
    void partTwo() {
        var input = List.of(
                new Motion('R', 5),
                new Motion('U', 8),
                new Motion('L', 8),
                new Motion('D', 3),
                new Motion('R', 17),
                new Motion('D', 10),
                new Motion('L', 25),
                new Motion('U', 20)
        );
        var day = new Day9();
        var numberOfPositions = day.partTwo(input);
        assertEquals(36, numberOfPositions);
    }
}