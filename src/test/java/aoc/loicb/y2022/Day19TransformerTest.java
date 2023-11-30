package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19TransformerTest {


    private static Stream<Arguments> getOreCostCaseProvider() {
        return Stream.of(
                Arguments.of("Each ore robot costs 4 ore.", 4),
                Arguments.of("Each clay robot costs 2 ore.", 2),
                Arguments.of("Each obsidian robot costs 3 ore and 14 clay.", 3),
                Arguments.of("Each geode robot costs 2 ore and 7 obsidian.", 2),
                Arguments.of("Each ore robot costs 2 ore.", 2),
                Arguments.of("Each clay robot costs 3 ore.", 3),
                Arguments.of("Each obsidian robot costs 3 ore and 8 clay.", 3),
                Arguments.of(" Each geode robot costs 3 ore and 12 obsidian.", 3)
        );
    }

    private static Stream<Arguments> getClayCostCaseProvider() {
        return Stream.of(
                Arguments.of("Each ore robot costs 4 ore.", 0),
                Arguments.of("Each clay robot costs 2 ore.", 0),
                Arguments.of("Each obsidian robot costs 3 ore and 14 clay.", 14),
                Arguments.of("Each geode robot costs 2 ore and 7 obsidian.", 0),
                Arguments.of("Each ore robot costs 2 ore.", 0),
                Arguments.of("Each clay robot costs 3 ore.", 0),
                Arguments.of("Each obsidian robot costs 3 ore and 8 clay.", 8),
                Arguments.of(" Each geode robot costs 3 ore and 12 obsidian.", 0)
        );
    }

    private static Stream<Arguments> getObsidianCostCaseProvider() {
        return Stream.of(
                Arguments.of("Each ore robot costs 4 ore.", 0),
                Arguments.of("Each clay robot costs 2 ore.", 0),
                Arguments.of("Each obsidian robot costs 3 ore and 14 clay.", 0),
                Arguments.of("Each geode robot costs 2 ore and 7 obsidian.", 7),
                Arguments.of("Each ore robot costs 2 ore.", 0),
                Arguments.of("Each clay robot costs 3 ore.", 0),
                Arguments.of("Each obsidian robot costs 3 ore and 8 clay.", 0),
                Arguments.of(" Each geode robot costs 3 ore and 12 obsidian.", 12)
        );
    }

    private static Stream<Arguments> getGeodeCostCaseProvider() {
        return Stream.of(
                Arguments.of("Each ore robot costs 4 ore.", 0),
                Arguments.of("Each clay robot costs 2 ore.", 0),
                Arguments.of("Each obsidian robot costs 3 ore and 14 clay.", 0),
                Arguments.of("Each geode robot costs 2 ore and 7 obsidian.", 0),
                Arguments.of("Each ore robot costs 2 ore.", 0),
                Arguments.of("Each clay robot costs 3 ore.", 0),
                Arguments.of("Each obsidian robot costs 3 ore and 8 clay.", 0),
                Arguments.of(" Each geode robot costs 3 ore and 12 obsidian.", 0)
        );
    }

    @Test
    void transformObject1() {
        String input = "Blueprint 1:" +
                "  Each ore robot costs 4 ore." +
                "  Each clay robot costs 2 ore." +
                "  Each obsidian robot costs 3 ore and 14 clay." +
                "  Each geode robot costs 2 ore and 7 obsidian.";
        var instance = new Day19Transformer();
        var output = instance.transformObject(input);
        var expected = new Blueprint(
                new Cost(4, 0, 0, 0),
                new Cost(2, 0, 0, 0),
                new Cost(3, 14, 0, 0),
                new Cost(2, 0, 7, 0)
        );
        assertEquals(expected, output);
    }

    @Test
    void transformObject2() {
        String input = "Blueprint 2:" +
                "  Each ore robot costs 2 ore." +
                "  Each clay robot costs 3 ore." +
                "  Each obsidian robot costs 3 ore and 8 clay." +
                "  Each geode robot costs 3 ore and 12 obsidian.";
        var instance = new Day19Transformer();
        var output = instance.transformObject(input);
        var expected = new Blueprint(
                new Cost(2, 0, 0, 0),
                new Cost(3, 0, 0, 0),
                new Cost(3, 8, 0, 0),
                new Cost(3, 0, 12, 0)
        );
        assertEquals(expected, output);
    }

    @ParameterizedTest
    @MethodSource("getOreCostCaseProvider")
    void getOreCost(String input, int expected) {
        var instance = new Day19Transformer();
        assertEquals(expected, instance.getOreCost(input));
    }

    @ParameterizedTest
    @MethodSource("getClayCostCaseProvider")
    void getClayCost(String input, int expected) {
        var instance = new Day19Transformer();
        assertEquals(expected, instance.getClayCost(input));
    }

    @ParameterizedTest
    @MethodSource("getObsidianCostCaseProvider")
    void getObsidianCost(String input, int expected) {
        var instance = new Day19Transformer();
        assertEquals(expected, instance.getObsidianCost(input));
    }

    @ParameterizedTest
    @MethodSource("getGeodeCostCaseProvider")
    void getGeodeCost(String input, int expected) {
        var instance = new Day19Transformer();
        assertEquals(expected, instance.getGeodeCost(input));
    }
}