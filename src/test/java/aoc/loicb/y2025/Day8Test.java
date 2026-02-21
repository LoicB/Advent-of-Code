package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

    private final List<JunctionBox> input =
            List.of(
                    new JunctionBox(162, 817, 812),
                    new JunctionBox(57, 618, 57),
                    new JunctionBox(906, 360, 560),
                    new JunctionBox(592, 479, 940),
                    new JunctionBox(352, 342, 300),
                    new JunctionBox(466, 668, 158),
                    new JunctionBox(542, 29, 236),
                    new JunctionBox(431, 825, 988),
                    new JunctionBox(739, 650, 466),
                    new JunctionBox(52, 470, 668),
                    new JunctionBox(216, 146, 977),
                    new JunctionBox(819, 987, 18),
                    new JunctionBox(117, 168, 530),
                    new JunctionBox(805, 96, 715),
                    new JunctionBox(346, 949, 466),
                    new JunctionBox(970, 615, 88),
                    new JunctionBox(941, 993, 340),
                    new JunctionBox(862, 61, 35),
                    new JunctionBox(984, 92, 344),
                    new JunctionBox(425, 690, 689)
            );

    private static Stream<Arguments> calcStraightLineDistanceCaseProvider() {
        return Stream.of(
                Arguments.of(new JunctionBox(162, 817, 812), new JunctionBox(425, 690, 689), 316.90219),
                Arguments.of(new JunctionBox(425, 690, 689), new JunctionBox(162, 817, 812), 316.90219),
                Arguments.of(new JunctionBox(162, 817, 812), new JunctionBox(431, 825, 988), 321.56025)
        );
    }

    @Test
    void partOne() {
        var day = new Day8();
        var circuitsSize = day.partOne(input, 10);
        assertEquals(40, circuitsSize);
    }

    @ParameterizedTest
    @MethodSource("calcStraightLineDistanceCaseProvider")
    void calcStraightLineDistance(JunctionBox junctionBox1, JunctionBox junctionBox2, double expectedDistance) {
        var day = new Day8();
        var distance = day.calcStraightLineDistance(junctionBox1, junctionBox2);
        assertEquals(expectedDistance, distance, 5.0);
    }

    @Test
    void partTwo() {
        var day = new Day8();
        var productOfX = day.partTwo(input);
        assertEquals(25272L, productOfX);
    }
}