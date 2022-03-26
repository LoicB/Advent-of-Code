package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day18Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]{"[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"}, 4140)
        );
    }

    private static Stream<Arguments> finalSumCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]{"[[[[4,3],4],4],[7,[[8,4],9]]]",
                        "[1,1]"}, "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
                Arguments.of(new String[]{"[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]"}, "[[[[1,1],[2,2]],[3,3]],[4,4]]"),
                Arguments.of(new String[]{"[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]",
                        "[5,5]"}, "[[[[3,0],[5,3]],[4,4]],[5,5]]"),
                Arguments.of(new String[]{"[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]",
                        "[5,5]",
                        "[6,6]"}, "[[[[5,0],[7,4]],[5,5]],[6,6]]"),
                Arguments.of(new String[]{"[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                        "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                        "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                        "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                        "[7,[5,[[3,8],[1,4]]]]",
                        "[[2,[2,2]],[8,[8,1]]]",
                        "[2,9]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                        "[[[5,[7,4]],7],1]",
                        "[[[[4,2],2],6],[8,7]]"}, "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"),
                Arguments.of(new String[]{"[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"}, "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]")
        );
    }

    private static Stream<Arguments> buildTreeCaseProvider() {
        return Stream.of(
                Arguments.of("[1,2]"),
                Arguments.of("[[1,2],3]"),
                Arguments.of("[9,[8,7]]"),
                Arguments.of("[[1,9],[8,5]]"),
                Arguments.of("[[1,2],[[3,4],5]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
                Arguments.of("[[[[1,1],[2,2]],[3,3]],[4,4]]"),
                Arguments.of("[[[[3,0],[5,3]],[4,4]],[5,5]]"),
                Arguments.of("[[[[5,0],[7,4]],[5,5]],[6,6]]"),
                Arguments.of("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"),
                Arguments.of("[15,[0,13]]"),
                Arguments.of("[[[[0,7],4],[15,[0,13]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]")
        );
    }

    private static Stream<Arguments> additionCaseProvider() {
        return Stream.of(
                Arguments.of("[1,2]", "[[3,4],5]", "[[1,2],[[3,4],5]]"),
                Arguments.of("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                        "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]", "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"),
                Arguments.of("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
                        "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                        "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]"),
                Arguments.of("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]",
                        "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                        "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]"),
                Arguments.of("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]",
                        "[7,[5,[[3,8],[1,4]]]]",
                        "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]"),
                Arguments.of("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]",
                        "[[2,[2,2]],[8,[8,1]]]",
                        "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]"),
                Arguments.of("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]",
                        "[2,9]",
                        "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"),
                Arguments.of("[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                        "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]"),
                Arguments.of("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]",
                        "[[[5,[7,4]],7],1]",
                        "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]"),
                Arguments.of("[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]",
                        "[[[[4,2],2],6],[8,7]]",
                        "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"),
                Arguments.of("[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]")
        );
    }

    private static Stream<Arguments> explodeCaseProvider() {
        return Stream.of(
                Arguments.of("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]", true),
                Arguments.of("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]", true),
                Arguments.of("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]", true),
                Arguments.of("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", true),
                Arguments.of("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]", true),
                Arguments.of("[[[[[1,1],[2,2]],[3,3]],[4,4]],[5,5]]", "[[[[0,[3,2]],[3,3]],[4,4]],[5,5]]", true),
                Arguments.of("[[[[0,[3,2]],[3,3]],[4,4]],[5,5]]", "[[[[3,0],[5,3]],[4,4]],[5,5]]", true),
                Arguments.of("[[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", "[[[[4,0],[5,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", true),
                Arguments.of("[[[[4,0],[5,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", "[[[[4,0],[5,4]],[[0,[7,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", true),
                Arguments.of("[[[[4,0],[5,4]],[[0,[7,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", "[[[[4,0],[5,4]],[[7,0],[15,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", true),
                Arguments.of("[[[[4,0],[5,4]],[[7,0],[[7,8],5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", "[[[[4,0],[5,4]],[[7,7],[0,13]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", true),
                Arguments.of("[[[[4,0],[5,4]],[[7,7],[0,[6,7]]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", "[[[[4,0],[5,4]],[[7,7],[6,0]]],[14,[[[3,7],[4,3]],[[6,3],[8,8]]]]]", true),
                Arguments.of("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[7,7],[[[3,7],[4,3]],[[6,3],[8,8]]]]]", "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[7,10],[[0,[11,3]],[[6,3],[8,8]]]]]", true),
                Arguments.of("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", false),
                Arguments.of("[[[[6,7],[0,7]],[[7,[6,7]],[0,21]]],[[2,[11,10]],[[0,8],[8,0]]]]", "[[[[6,7],[0,7]],[[13,0],[7,21]]],[[2,[11,10]],[[0,8],[8,0]]]]", true),
                Arguments.of("[[[[7,7],[7,7]],[[7,8],[0,8]]],[[[8,9],[[5,5],10]],[[21,10],[5,0]]]]", "[[[[7,7],[7,7]],[[7,8],[0,8]]],[[[8,14],[0,15]],[[21,10],[5,0]]]]", true),
                Arguments.of("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[9,[5,5]],[[[5,5],0],[11,0]]]]", "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[9,[5,10]],[[0,5],[11,0]]]]", true),
                Arguments.of("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[9,[5,5]],[[10,0],[11,0]]]]", "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[9,[5,5]],[[10,0],[11,0]]]]", false)
        );
    }

    private static Stream<Arguments> splitCaseProvider() {
        return Stream.of(
                Arguments.of("[[[[0,7],4],[15,[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
                Arguments.of("[[[[5,9],[0,[16,6]]],[[4,[1,2]],[[1,4],2]]],[[[5,[2,8]],4],[5,[[9,9],0]]]]", "[[[[5,9],[8,[0,14]]],[[4,[1,2]],[[1,4],2]]],[[[5,[2,8]],4],[5,[[9,9],0]]]]"),
                Arguments.of("[[[[5,9],[8,[0,14]]],[[4,[1,2]],[[1,4],2]]],[[[5,[2,8]],4],[5,[[9,9],0]]]]", "[[[[5,9],[8,[7,0]]],[[11,[1,2]],[[1,4],2]]],[[[5,[2,8]],4],[5,[[9,9],0]]]]"),
                Arguments.of("[[[[5,9],[8,[7,0]]],[[11,[1,2]],[[1,4],2]]],[[[5,[2,8]],4],[5,[[9,9],0]]]]", "[[[[5,9],[8,[7,5]]],[[0,[7,2]],[[1,4],2]]],[[[5,[2,8]],4],[5,[[9,9],0]]]]"),
                Arguments.of("[[[[4,0],[5,4]],[[7,0],[15,5]]],[10,[[11,9],[11,0]]]]", "[[[[4,0],[5,4]],[[7,7],[0,13]]],[10,[[11,9],[11,0]]]]"),
                Arguments.of("[[[[6,7],[0,7]],[[7,13],[0,21]]],[[2,[11,10]],[[0,8],[8,0]]]]", "[[[[6,7],[0,7]],[[13,0],[7,21]]],[[2,[11,10]],[[0,8],[8,0]]]]"),
                Arguments.of("[[[[7,7],[7,7]],[[7,8],[0,8]]],[[[8,9],[10,10]],[[21,10],[5,0]]]]", "[[[[7,7],[7,7]],[[7,8],[0,8]]],[[[8,14],[0,15]],[[21,10],[5,0]]]]"),
                Arguments.of("[[[[7,7],[7,8]],[[9,5],[8,0]]],[[[9,10],20],[8,[9,0]]]]", "[[[[7,7],[7,8]],[[9,5],[8,0]]],[[[14,0],25],[8,[9,0]]]]"),
                Arguments.of("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[9,[5,5]],[[10,0],[11,0]]]]", "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[9,[5,10]],[[0,5],[11,0]]]]")
        );
    }

    private static Stream<Arguments> calculateMagnitudeCaseProvider() {
        return Stream.of(
                Arguments.of("[9,1]", 29),
                Arguments.of("[1,9]", 21),
                Arguments.of("[[9,1],[1,9]]", 129),
                Arguments.of("[[1,2],[[3,4],5]]", 143),
                Arguments.of("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", 1384),
                Arguments.of("[[[[1,1],[2,2]],[3,3]],[4,4]]", 445),
                Arguments.of("[[[[3,0],[5,3]],[4,4]],[5,5]]", 791),
                Arguments.of("[[[[5,0],[7,4]],[5,5]],[6,6]]", 1137),
                Arguments.of("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", 3488),
                Arguments.of("[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]", 3993)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]{"[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"}, 3993)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String[] fiches, int expectedMagnitude) {
        Day18 day = new Day18();
        int magnitude = day.partOne(fiches);
        assertEquals(expectedMagnitude, magnitude);
    }

    @ParameterizedTest
    @MethodSource("finalSumCaseProvider")
    void finalSum(String[] fiches, String expectedSum) {
        Day18 day = new Day18();
        SnailfishNumber result = day.finalSum(Arrays.stream(fiches).map(day::buildTree).collect(Collectors.toList()));
        assertEquals(expectedSum, result.toString());
    }

    @ParameterizedTest
    @MethodSource("buildTreeCaseProvider")
    void buildTree(String input) {
        Day18 day = new Day18();
        SnailfishNumber snailfishNumber = day.buildTree(input);
        assertEquals(snailfishNumber.toString(), input);
    }

    @ParameterizedTest
    @MethodSource("additionCaseProvider")
    void addition(String number1, String number2, String expected) {
        Day18 day = new Day18();
        SnailfishNumber snailfishNumber = day.addition(day.buildTree(number1), day.buildTree(number2));
        assertEquals(expected, snailfishNumber.toString());
    }

    @ParameterizedTest
    @MethodSource("explodeCaseProvider")
    void explode(String number, String expected, boolean expectedModified) {
        Day18 day = new Day18();
        SnailfishNumber snailfishNumber = day.buildTree(number);
        boolean modified = day.explode(snailfishNumber);
        assertEquals(expectedModified, modified);
        assertEquals(expected, snailfishNumber.toString());
    }

    @ParameterizedTest
    @MethodSource("splitCaseProvider")
    void split(String number, String expected) {
        Day18 day = new Day18();
        SnailfishNumber snailfishNumber = day.buildTree(number);
        boolean modified = day.split(snailfishNumber);
        assertTrue(modified);
        assertEquals(expected, snailfishNumber.toString());
    }

    @ParameterizedTest
    @MethodSource("calculateMagnitudeCaseProvider")
    void calculateMagnitude(String number, int expectedMagnitude) {
        Day18 day = new Day18();
        int magnitude = day.calculateMagnitude(day.buildTree(number));
        assertEquals(expectedMagnitude, magnitude);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String[] fiches, int expectedMagnitude) {
        Day18 day = new Day18();
        int magnitude = day.partTwo(fiches);
        assertEquals(expectedMagnitude, magnitude);
    }

}