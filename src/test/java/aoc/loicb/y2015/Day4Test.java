package aoc.loicb.y2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("abcdef", 609043),
                Arguments.of("pqrstuv", 1048970)
        );
    }

    private static Stream<Arguments> isMd5HashValidCaseProvider() {
        return Stream.of(
                Arguments.of("abcdef1", false),
                Arguments.of("abcdef72162", false),
                Arguments.of("abcdef609042", false),
                Arguments.of("abcdef609043", true),
                Arguments.of("pqrstuv1", false),
                Arguments.of("pqrstuv286058", false),
                Arguments.of("pqrstuv1048969", false),
                Arguments.of("pqrstuv1048970", true)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String input, int expectedHash) {
        Day4 day = new Day4();
        int md5Hash = day.partOne(input);
        assertEquals(expectedHash, md5Hash);
    }

    @ParameterizedTest
    @MethodSource("isMd5HashValidCaseProvider")
    void isMd5HashValid(String input, boolean expectedValidity) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        Day4 day = new Day4();
        boolean valid = day.isMd5HashValid(md.digest());
        assertEquals(expectedValidity, valid);
    }

    @Test
    void partTwo() {
    }
}