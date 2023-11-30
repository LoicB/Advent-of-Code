package aoc.loicb.y2022;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day19Test {

    private static List<Blueprint> prepareTestParameter() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2022/Day19.txt"));
        DayExecutor<List<Blueprint>> de = new DayExecutor<>(new Day19Transformer());
        return de.transformer().transform(rawInput);
    }

    private static Stream<Arguments> largestNumberOfGeodeCaseProvider() {
        return Stream.of(
                Arguments.of(0, 24, 9),
                Arguments.of(1, 24, 12),
                Arguments.of(0, 32, 56),
                Arguments.of(1, 32, 62)
        );
    }

    private static Stream<Arguments> canBuildRobotCaseProvider() {
        return Stream.of(
                Arguments.of(new State(new int[]{0, 0, 16, 2}, new int[]{0, 0, 4, 1}), false),
                Arguments.of(new State(new int[]{0, 0, 16, 3}, new int[]{0, 0, 4, 1}), true)
        );
    }

    private static Stream<Arguments> getPossibleStatesCaseProvider() {
        return Stream.of(
                Arguments.of(new State(new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 1}), new State(new int[]{0, 0, 0, 1}, new int[]{0, 0, 0, 1})),
                Arguments.of(new State(new int[]{0, 0, 0, 1}, new int[]{0, 0, 0, 1}), new State(new int[]{0, 0, 0, 2}, new int[]{0, 0, 0, 1})),
                Arguments.of(new State(new int[]{0, 0, 0, 2}, new int[]{0, 0, 0, 1}), new State(new int[]{0, 0, 0, 1}, new int[]{0, 0, 1, 1})),
                Arguments.of(new State(new int[]{0, 0, 0, 1}, new int[]{0, 0, 1, 1}), new State(new int[]{0, 0, 1, 2}, new int[]{0, 0, 1, 1})),
                Arguments.of(new State(new int[]{0, 0, 1, 2}, new int[]{0, 0, 1, 1}), new State(new int[]{0, 0, 2, 1}, new int[]{0, 0, 2, 1})),
                Arguments.of(new State(new int[]{0, 0, 2, 1}, new int[]{0, 0, 2, 1}), new State(new int[]{0, 0, 4, 2}, new int[]{0, 0, 2, 1})),
                Arguments.of(new State(new int[]{0, 0, 4, 2}, new int[]{0, 0, 2, 1}), new State(new int[]{0, 0, 6, 1}, new int[]{0, 0, 3, 1})),
                Arguments.of(new State(new int[]{0, 0, 6, 1}, new int[]{0, 0, 3, 1}), new State(new int[]{0, 0, 9, 2}, new int[]{0, 0, 3, 1})),
                Arguments.of(new State(new int[]{0, 0, 9, 2}, new int[]{0, 0, 3, 1}), new State(new int[]{0, 0, 12, 3}, new int[]{0, 0, 3, 1})),
                Arguments.of(new State(new int[]{0, 0, 12, 3}, new int[]{0, 0, 3, 1}), new State(new int[]{0, 0, 15, 4}, new int[]{0, 0, 3, 1})),
                Arguments.of(new State(new int[]{0, 0, 15, 4}, new int[]{0, 0, 3, 1}), new State(new int[]{0, 0, 4, 2}, new int[]{0, 1, 3, 1}))
        );
    }

    @Test
    void partOne() throws IOException {
        List<Blueprint> blueprints = prepareTestParameter();
        var day = new Day19();
        var qualityLevels = day.partOne(blueprints);
        assertEquals(33, qualityLevels);
    }

    @ParameterizedTest
    @MethodSource("largestNumberOfGeodeCaseProvider")
    void largestNumberOfGeode(int index, int time, int expected) throws IOException {
        List<Blueprint> blueprints = prepareTestParameter();
        var day = new Day19();
        var numberOfGeode = day.largestNumberOfGeodes(blueprints.get(index), time);
        assertEquals(expected, numberOfGeode);
    }

    @ParameterizedTest
    @MethodSource("canBuildRobotCaseProvider")
    void canBuildRobot(State state, boolean expected) throws IOException {
        List<Blueprint> blueprints = prepareTestParameter();
        var day = new Day19();
        var possible = day.canBuildRobot(blueprints.get(0).obsidianRobotCost(), state);
        assertEquals(expected, possible);
    }

    @ParameterizedTest
    @MethodSource("getPossibleStatesCaseProvider")
    void getPossibleStates(State state, State expected) throws IOException {
        List<Blueprint> blueprints = prepareTestParameter();
        var day = new Day19();
        var possible = day.getPossibleStates(blueprints.get(0), state);
        assertTrue(possible.contains(expected));
    }

    @ParameterizedTest
    @MethodSource("getPossibleStatesCaseProvider")
    void filterStates(State state, State expected) throws IOException {
        List<Blueprint> blueprints = prepareTestParameter();
        var day = new Day19();
        var possible = day.filterStates(new HashSet<>(day.getPossibleStates(blueprints.get(0), state)));
        assertTrue(possible.contains(expected));
    }

    @Test
    void partTwo() throws IOException {
        List<Blueprint> blueprints = prepareTestParameter();
        var day = new Day19();
        var qualityLevels = day.partTwo(blueprints);
        assertEquals(62 * 56, qualityLevels);
    }

}