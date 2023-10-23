package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {

    private static Stream<Arguments> commonSidesCountCaseProvider() {
        return Stream.of(
                Arguments.of(new LavaDroplet(1, 1, 1), new LavaDroplet(2, 1, 1), 2),
                Arguments.of(new LavaDroplet(1, 1, 1), new LavaDroplet(3, 1, 1), 0)
        );
    }

    List<LavaDroplet> getSample() {
        return List.of(
                new LavaDroplet(2, 2, 2),
                new LavaDroplet(1, 2, 2),
                new LavaDroplet(3, 2, 2),
                new LavaDroplet(2, 1, 2),
                new LavaDroplet(2, 3, 2),
                new LavaDroplet(2, 2, 1),
                new LavaDroplet(2, 2, 3),
                new LavaDroplet(2, 2, 4),
                new LavaDroplet(2, 2, 6),
                new LavaDroplet(1, 2, 5),
                new LavaDroplet(3, 2, 5),
                new LavaDroplet(2, 1, 5),
                new LavaDroplet(2, 3, 5)
        );
    }

    //exposedSidesCount

    @Test
    void partOne() {
        var droplets = getSample();
        var day = new Day18();
        long numberOfSides = day.partOne(droplets);
        assertEquals(64, numberOfSides);
    }

    @ParameterizedTest
    @MethodSource("commonSidesCountCaseProvider")
    void commonSidesCount(LavaDroplet droplet1, LavaDroplet droplet2, int expectedSidesCount) {
        var day = new Day18();
        int sidesCount = day.commonSidesCount(droplet1, droplet2);
        assertEquals(expectedSidesCount, sidesCount);
    }


    @Test
    void partTwo() {
        var droplets = getSample();
        var day = new Day18();
        long numberOfSides = day.partTwo(droplets);
        assertEquals(58, numberOfSides);
    }

    @Test
    void testgetAdjacentCubes() {

        var day = new Day18();
        System.out.println(day.getAdjacentCubes(new LavaDroplet(0, 0, 6)));
    }

}