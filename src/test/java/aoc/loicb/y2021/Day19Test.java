package aoc.loicb.y2021;

import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
    private static Stream<Arguments> getScannerPositionCaseProvider() {
        int[] centralPosition = new int[]{0, 0, 0};
        return Stream.of(
                Arguments.of(0, 1, centralPosition, true, new int[]{68, -1246, -43}),
                Arguments.of(0, 2, centralPosition, true, new int[]{1105, -1205, 1229}),
                Arguments.of(0, 3, centralPosition, false, new int[]{-92, -2380, -20}),
                Arguments.of(0, 4, centralPosition, true, new int[]{-20, -1133, 1061}),
                Arguments.of(1, 2, new int[]{68, -1246, -43}, true, new int[]{1105, -1205, 1229}),
                Arguments.of(1, 3, new int[]{68, -1246, -43}, true, new int[]{-92, -2380, -20}),
                Arguments.of(1, 4, new int[]{68, -1246, -43}, true, new int[]{-20, -1133, 1061}),
                Arguments.of(2, 3, new int[]{1105, -1205, 1229}, true, new int[]{-92, -2380, -20}),
                Arguments.of(2, 4, new int[]{1105, -1205, 1229}, true, new int[]{-20, -1133, 1061})
        );
    }

    private static Stream<Arguments> createVectorCaseProvider() {
        return Stream.of(
                Arguments.of("1994,-1805,1792", new int[]{1994, -1805, 1792})
        );
    }

    private static Stream<Arguments> calculateOriginCaseProvider() {
        int[] origin1 = new int[]{5, 2};
        int[] origin2 = new int[]{68, -1246, -43};
        return Stream.of(
                Arguments.of(new Beacon(0, 2), new Beacon(-5, 0), origin1),
                Arguments.of(new Beacon(4, 1), new Beacon(-1, -1), origin1),
                Arguments.of(new Beacon(3, 3), new Beacon(-2, 1), origin1),
                Arguments.of(new Beacon(0, 2), new Beacon(0, 2), new int[]{0, 0}),
                Arguments.of(new Beacon(-618, -824, -621), new Beacon(-686, 422, -578), origin2),
                Arguments.of(new Beacon(-537, -823, -458), new Beacon(-605, 423, -415), origin2),
                Arguments.of(new Beacon(-447, -329, 318), new Beacon(-515, 917, 361), origin2),
                Arguments.of(new Beacon(404, -588, -901), new Beacon(336, 658, -858), origin2),
                Arguments.of(new Beacon(544, -627, -890), new Beacon(476, 619, -847), origin2),
                Arguments.of(new Beacon(528, -643, 409), new Beacon(460, 603, 452), origin2),
                Arguments.of(new Beacon(-661, -816, -575), new Beacon(-729, 430, -532), origin2),
                Arguments.of(new Beacon(390, -675, -793), new Beacon(322, 571, -750), origin2),
                Arguments.of(new Beacon(423, -701, 434), new Beacon(355, 545, 477), origin2),
                Arguments.of(new Beacon(-345, -311, 381), new Beacon(-413, 935, 424), origin2),
                Arguments.of(new Beacon(459, -707, 401), new Beacon(391, 539, 444), origin2),
                Arguments.of(new Beacon(-485, -357, 347), new Beacon(-553, 889, 390), origin2)
        );
    }

    private static Stream<Arguments> beaconsEqualCaseProvider() {
        return Stream.of(
                Arguments.of(new Beacon(0, 2), new Beacon(4, 1), false),
                Arguments.of(new Beacon(0, 2), new Beacon(3, 3), false),
                Arguments.of(new Beacon(0, 2), new Beacon(0, 2), true),
                Arguments.of(new Beacon(4, 1), new Beacon(4, 1), true),
                Arguments.of(new Beacon(3, 3), new Beacon(3, 3), true)
        );
    }

    @Test
    void partOne() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day19.txt"));
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        String[] input = de.transformer().transform(rawInput);
        Day19 day = new Day19();
        List<Scanner> scanners = Day19.transformInput(input);
        int beacons = day.partOne(scanners);
        assertEquals(3, beacons);
    }

    @org.junit.jupiter.api.Test
    void partTwo() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day19_2.txt"));
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        String[] input = de.transformer().transform(rawInput);
        Day19 day = new Day19();
        List<Scanner> scanners = Day19.transformInput(input);
        int beacons = day.partTwo(scanners);
        assertEquals(3621, beacons);
    }

    @Test
    void partOneSample2() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day19_2.txt"));
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        String[] input = de.transformer().transform(rawInput);
        Day19 day = new Day19();
        List<Scanner> scanners = Day19.transformInput(input);
        int beacons = day.partOne(scanners);
        assertEquals(79, beacons);
    }

    @ParameterizedTest
    @MethodSource("getScannerPositionCaseProvider")
    void getScannerPosition(int index1, int index2, int[] referencePoint, boolean matches, int[] expectedPosition) throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day19_2.txt"));
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        String[] input = de.transformer().transform(rawInput);
        Day19 day = new Day19();
        List<Scanner> scanners = Day19.transformInput(input);
        if (index1 != 0)
            day.getScannerPosition(scanners.get(0), new int[]{0, 0, 0}, scanners.get(index1), new HashMap<>());
        Optional<int[]> position = day.getScannerPosition(scanners.get(index1), referencePoint, scanners.get(index2), new HashMap<>());
        assertEquals(matches, position.isPresent());
        position.ifPresent(pos -> assertArrayEquals(expectedPosition, pos));
    }

    @ParameterizedTest
    @MethodSource("createVectorCaseProvider")
    void createVector(String input, int[] expected) {
        int[] vector = Day19.createVector(input);
        assertArrayEquals(expected, vector);
    }

    @ParameterizedTest
    @MethodSource("calculateOriginCaseProvider")
    void calculateOrigin(Beacon reference, Beacon target, int[] expected) {
        Day19 day = new Day19();
        int[] origin = day.calculateOrigin(reference, target);
        assertArrayEquals(expected, origin);
    }

    @ParameterizedTest
    @MethodSource("calculateOriginCaseProvider")
    void updateBeaconCoordinates(Beacon reference, Beacon target, int[] origin) {
        Day19 day = new Day19();
        int[] coordinates = day.updateBeaconCoordinates(target, origin);
        assertArrayEquals(reference.getData(), coordinates);
    }

    @ParameterizedTest
    @MethodSource("beaconsEqualCaseProvider")
    void beaconsEqual(Beacon beacon1, Beacon beacon2, boolean equals) {
        assertEquals(beacon1.equals(beacon2), equals);
    }
}