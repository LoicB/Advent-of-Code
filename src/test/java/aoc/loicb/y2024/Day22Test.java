package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22Test {

    private static Stream<Arguments> calculateFinalSecretNumberCaseProvider() {
        return Stream.of(
                Arguments.of(1, 8685429),
                Arguments.of(10, 4700978),
                Arguments.of(100, 15273692),
                Arguments.of(2024, 8667524)
        );
    }

    private static Stream<Arguments> calculateNextSecretNumberCaseProvider() {
        return Stream.of(
                Arguments.of(123, 15887950),
                Arguments.of(15887950, 16495136),
                Arguments.of(16495136, 527345),
                Arguments.of(527345, 704524),
                Arguments.of(704524, 1553684),
                Arguments.of(1553684, 12683156),
                Arguments.of(12683156, 11100544),
                Arguments.of(11100544, 12249484),
                Arguments.of(12249484, 7753432),
                Arguments.of(7753432, 5908254)
        );
    }

    @Test
    void partOne() {
        var input = List.of(1,
                10,
                100,
                2024);
        var day = new Day22();
        var secretNumbers = day.partOne(input);
        assertEquals(37327623, secretNumbers);
    }

    @ParameterizedTest
    @MethodSource("calculateFinalSecretNumberCaseProvider")
    void calculateFinalSecretNumber(int initialSecretNumber, int expectedSecretNumber) {
        var day = new Day22();
        var secretNumber = day.calculateFinalSecretNumber(initialSecretNumber);
        assertEquals(expectedSecretNumber, secretNumber);
    }

    @ParameterizedTest
    @MethodSource("calculateNextSecretNumberCaseProvider")
    void calculateNextSecretNumber(int initialSecretNumber, int expectedSecretNumber) {
        var day = new Day22();
        var secretNumber = day.calculateNextSecretNumber(initialSecretNumber);
        assertEquals(expectedSecretNumber, secretNumber);
    }

    @Test
    void partTwo() {
        var input = List.of(1,
                2,
                3,
                2024);
        var day = new Day22();
        var bananas = day.partTwo(input);
        assertEquals(23, bananas);
    }

    @Test
    void createBananaChangeIndex() {
        var day = new Day22();
        var changesIndex = day.createBananaChangeIndex(123, 10);
        assertEquals(changesIndex.get(new Day22.Changes(-1, -1, 0, 2)), 6);
        assertEquals(changesIndex.get(new Day22.Changes(-3, 6, -1, -1)), 4);
        assertEquals(changesIndex.get(new Day22.Changes(2, -2, 0, -2)), 2);
    }
}