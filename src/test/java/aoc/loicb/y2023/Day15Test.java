package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    private static Stream<Arguments> hashAlgorithmCaseProvider() {
        return Stream.of(
                Arguments.of("HASH", 52),
                Arguments.of("rn=1", 30),
                Arguments.of("cm-", 253),
                Arguments.of("qp=3", 97),
                Arguments.of("cm=2", 47),
                Arguments.of("qp-", 14),
                Arguments.of("pc=4", 180),
                Arguments.of("ot=9", 9),
                Arguments.of("ab=5", 197),
                Arguments.of("pc-", 48),
                Arguments.of("pc=6", 214),
                Arguments.of("ot=7", 231)
        );
    }

    @Test
    void partOne() {
        var sequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
        var day = new Day15();
        var sum = day.partOne(sequence);
        assertEquals(1320, sum);

    }

    @ParameterizedTest
    @MethodSource("hashAlgorithmCaseProvider")
    void hashAlgorithm(String string, int expectedHash) {
        var day = new Day15();
        var hash = day.hashAlgorithm(string);
        assertEquals(expectedHash, hash);
    }


    @Test
    void partTwo() {
        var sequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
        var day = new Day15();
        var sum = day.partTwo(sequence);
        assertEquals(145, sum);
    }

    @Test
    void buildBoxes() {
        var sequence = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
        var day = new Day15();
        var boxes = day.buildBoxes(sequence);
        assertEquals(2, boxes.get(0).getContent().size());
        assertEquals(3, boxes.get(3).getContent().size());
    }
}