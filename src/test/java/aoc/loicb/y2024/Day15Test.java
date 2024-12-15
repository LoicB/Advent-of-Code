package aoc.loicb.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    private final static Day15Input INPUT_1 = new Day15Input(
            new char[][]{
                    "########".toCharArray(),
                    "#..O.O.#".toCharArray(),
                    "##@.O..#".toCharArray(),
                    "#...O..#".toCharArray(),
                    "#.#.O..#".toCharArray(),
                    "#...O..#".toCharArray(),
                    "#......#".toCharArray(),
                    "########".toCharArray()
            },
            "<^^>>>vv<v>>v<<".chars().mapToObj(operand -> (char) operand).toList()
    );
    private final static Day15Input INPUT_2 = new Day15Input(
            new char[][]{
                    "##########".toCharArray(),
                    "#..O..O.O#".toCharArray(),
                    "#......O.#".toCharArray(),
                    "#.OO..O.O#".toCharArray(),
                    "#..O@..O.#".toCharArray(),
                    "#O#..O...#".toCharArray(),
                    "#O..O..O.#".toCharArray(),
                    "#.OO.O.OO#".toCharArray(),
                    "#....O...#".toCharArray(),
                    "##########".toCharArray()
            },
            ("<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^" +
                    "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v" +
                    "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<" +
                    "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^" +
                    "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><" +
                    "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^" +
                    ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^" +
                    "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>" +
                    "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>" +
                    "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^").chars().mapToObj(operand -> (char) operand).toList()
    );


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_1, 2028),
                Arguments.of(INPUT_2, 10092)
        );
    }

    private static Stream<Arguments> findPositionCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_1, 2, 2),
                Arguments.of(INPUT_2, 4, 4)
        );
    }

    private static Stream<Arguments> moveRobotCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_1, '>', 3, 2),
                Arguments.of(INPUT_1, 'v', 2, 3),
                Arguments.of(INPUT_1, '<', 2, 2),
                Arguments.of(INPUT_1, '^', 2, 1),
                Arguments.of(INPUT_2, '>', 5, 4),
                Arguments.of(INPUT_2, 'v', 4, 5),
                Arguments.of(INPUT_2, '<', 3, 4),
                Arguments.of(INPUT_2, '^', 4, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(Day15Input input, int expectedSum) {
        var day = new Day15();
        var boxesSum = day.partOne(input);
        assertEquals(expectedSum, boxesSum);
    }

    @ParameterizedTest
    @MethodSource("findPositionCaseProvider")
    void findPosition(Day15Input input, int expectedX, int expectedY) {
        var day = new Day15();
        var position = day.findPosition(input.map());
        assertEquals(expectedX, position.x());
        assertEquals(expectedY, position.y());
    }

    @ParameterizedTest
    @MethodSource("moveRobotCaseProvider")
    void moveRobot(Day15Input input, char direction, int expectedX, int expectedY) {
        var day = new Day15();
        char[][] map = day.copyMap(input.map());
        System.out.println(day.findPosition(map));
        var position = day.moveRobot(map, day.findPosition(map), direction);
        System.out.println(position);
        assertEquals(expectedX, position.x());
        assertEquals(expectedY, position.y());
    }

    private final static Day15Input INPUT_3 = new Day15Input(
            new char[][]{
                    "#######".toCharArray(),
                    "#...#.#".toCharArray(),
                    "#.....#".toCharArray(),
                    "#..OO@#".toCharArray(),
                    "#..O..#".toCharArray(),
                    "#.....#".toCharArray(),
                    "#######".toCharArray()
            },
            "<vv<<^^<<^^".chars().mapToObj(operand -> (char) operand).toList()
    );

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_2, 9021),
                Arguments.of(INPUT_3, 618)
        );
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(Day15Input input, int expectedSum) {
        var day = new Day15();
        var boxesSum = day.partTwo(input);
        assertEquals(expectedSum, boxesSum);
    }
}