package aoc.loicb.y2021;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {

    private static Stream<Arguments> pixelValueCaseProvider() {
        return Stream.of(
                Arguments.of(new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.'}, 0),
                Arguments.of(new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '#'}, 1),
                Arguments.of(new char[]{'.', '.', '.', '.', '.', '.', '.', '#', '.'}, 2),
                Arguments.of(new char[]{'.', '.', '.', '#', '.', '.', '.', '#', '.'}, 34),
                Arguments.of(new char[]{'#', '.', '.', '.', '.', '.', '.', '.', '.'}, 256),
                Arguments.of(new char[]{'#', '#', '.', '.', '.', '#', '.', '.', '#'}, 393),
                Arguments.of(new char[]{'#', '#', '#', '#', '#', '#', '#', '#', '#'}, 511)

        );
    }

//    @Test
//    void enhanceImage() throws IOException {
//        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day20.txt"));
//        DayExecutor<Day20Input> de = new DayExecutor<>(new InputToImage());
//        Day20Input input = de.transformer().transform(rawInput);
//        Day20 day = new Day20();
//        Map<Point, Character> newImage = day.enhanceImage(input.imageEnhancementAlgorithm(), input.image());
//        String rawExpected =
//                        "...............\n" +
//                        "...............\n" +
//                        "...............\n" +
//                        "...............\n" +
//                        ".....##.##.....\n" +
//                        "....#..#.#.....\n" +
//                        "....##.#..#....\n" +
//                        "....####..#....\n" +
//                        ".....#..##.....\n" +
//                        "......##..#....\n" +
//                        ".......#.#.....\n" +
//                        "...............\n" +
//                        "...............\n" +
//                        "...............\n" +
//                        "...............";
//        Map<Point, Character> expectedImage = new HashMap<>();
//        String[] inputLines = rawExpected.split("\\r?\\n");
//        for (int i = 0; i < inputLines.length; i++) {
//            for (int j = 0; j < inputLines[i].length(); j++) {
//                expectedImage.put(new Point(i - 2, j), inputLines[i].charAt(j));
//            }
//        }
//        assertEquals(expectedImage, newImage);
//    }


//    private final static char[][] SAMPLE1 = new char[][]{
//            {'#', '.', '.', '#', '.'},
//            {'#', '.', '.', '.', '.'},
//            {'#', '#', '.', '.', '#'},
//            {'.', '.', '#', '.', '.'},
//            {'.', '.', '#', '#', '#'}
//    };


//    private static Stream<Arguments> getNeighbourPixelsCaseProvider() {
//        return Stream.of(
//                Arguments.of(SAMPLE1, 0, 0, new char[]{'.', '.', '.', '.', '#', '.', '.', '#', '.'}),
//                Arguments.of(SAMPLE1, 2, 2, new char[]{'.', '.', '.', '#', '.', '.', '.', '#', '.'}),
//                Arguments.of(SAMPLE1, 3, 1, new char[]{'#', '#', '.', '.', '.', '#', '.', '.', '#'})
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("getNeighbourPixelsCaseProvider")
//    void getNeighbourPixels(char[][] image, int x, int y, char[] expectedPixels) {
//        Day20 day = new Day20();
//        char[] pixels = day.getNeighbourPixels(image, x, y);
//        assertArrayEquals(expectedPixels, pixels);
//    }

    @Test
    void partOne() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day20.txt"));
        DayExecutor<Day20Input> de = new DayExecutor<>(new InputToImage());
        Day20Input input = de.transformer().transform(rawInput);
        Day20 day = new Day20();
        int score = day.partOne(input);
        assertEquals(35, score);
    }

    @ParameterizedTest
    @MethodSource("pixelValueCaseProvider")
    void pixelValue(char[] neighbourPixels, int expectedValue) {
        Day20 day = new Day20();
        int value = day.pixelValue(neighbourPixels);
        assertEquals(expectedValue, value);
    }

    @Test
    void partTwo() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day20.txt"));
        DayExecutor<Day20Input> de = new DayExecutor<>(new InputToImage());
        Day20Input input = de.transformer().transform(rawInput);
        Day20 day = new Day20();
        int score = day.partTwo(input);
        assertEquals(3351, score);
    }
}