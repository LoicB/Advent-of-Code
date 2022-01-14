package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("EE00D40C823060", 14),
                Arguments.of("38006F45291200", 9),
                Arguments.of("8A004A801A8002F478", 16),
                Arguments.of("620080001611562C8802118E34", 12),
                Arguments.of("C0015000016115A2E0802F182340", 23),
                Arguments.of("A0016C880162017C3686B18A3D4780", 31)
        );
    }

    private static Stream<Arguments> getVersionCaseProvider() {
        return Stream.of(
                Arguments.of("11101110000000001101010000001100100000100011000001100000", 7),
                Arguments.of("00111000000000000110111101000101001010010001001000000000", 1),
                Arguments.of("100010100000000001001010100000000001101010000000000000101111010001111000", 4),
                Arguments.of("01100010000000001000000000000000000101100001000101010110001011001000100000000010000100011000111000110100", 3),
                Arguments.of("1100000000000001010100000000000000000001011000010001010110100010111000001000000000101111000110000010001101000000", 6),
                Arguments.of("101000000000000101101100100010000000000101100010000000010111110000110110100001101011000110001010001111010100011110000000", 5),
                Arguments.of("11010001010", 6),
                Arguments.of("0101001000100100", 2)
        );
    }

    private static Stream<Arguments> getTypeIdCaseProvider() {
        return Stream.of(
                Arguments.of("110100101111111000101000", TypeId.literal),
                Arguments.of("00111000000000000110111101000101001010010001001000000000", TypeId.less)
        );
    }

    private static Stream<Arguments> isNumberCaseProvider() {
        return Stream.of(
                Arguments.of("110100101111111000101000", true),
                Arguments.of("00111000000000000110111101000101001010010001001000000000", false),
                Arguments.of("11101110000000001101010000001100100000100011000001100000", false),
                Arguments.of("100010100000000001001010100000000001101010000000000000101111010001111000", false),
                Arguments.of("01100010000000001000000000000000000101100001000101010110001011001000100000000010000100011000111000110100", false),
                Arguments.of("1100000000000001010100000000000000000001011000010001010110100010111000001000000000101111000110000010001101000000", false),
                Arguments.of("101000000000000101101100100010000000000101100010000000010111110000110110100001101011000110001010001111010100011110000000", false)
        );
    }

    private static Stream<Arguments> getValueCaseProvider() {
        return Stream.of(
                Arguments.of("110100101111111000101000", 6, 2021),
                Arguments.of("00111000000000000110111101000101001010010001001000000000", 28, 10),
                Arguments.of("00111000000000000110111101000101001010010001001000000000", 39, 20)
        );
    }

    private static Stream<Arguments> readPacketCaseProvider() {
        return Stream.of(
                Arguments.of("00111000000000000110111101000101001010010001001000000000", 9),
                Arguments.of("11101110000000001101010000001100100000100011000001100000", 14)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of("C200B40A82", 3),
                Arguments.of("04005AC33890", 54),
                Arguments.of("880086C3E88112", 7),
                Arguments.of("CE00C43D881120", 9),
                Arguments.of("D8005AC2A8F0", 1),
                Arguments.of("F600BC2D8F", 0),
                Arguments.of("9C005AC2F8F0", 0),
                Arguments.of("9C0141080250320F1802104A08", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String packet, int expectedVersionSum) {
        Day16 day = new Day16();
        int sum = day.partOne(packet.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList()));
        assertEquals(expectedVersionSum, sum);
    }

    @ParameterizedTest
    @MethodSource("getVersionCaseProvider")
    void getVersion(String bits, int expectedVersion) {
        Day16 day = new Day16();
        int version = day.getVersion(new Packet(bits));
        assertEquals(expectedVersion, version);
    }

    @ParameterizedTest
    @MethodSource("getTypeIdCaseProvider")
    void getTypeId(String bits, TypeId expectedTypeId) {
        Day16 day = new Day16();
        TypeId typeId = day.getTypeId(new Packet(bits, 3));
        assertEquals(expectedTypeId, typeId);
    }

    @ParameterizedTest
    @MethodSource("isNumberCaseProvider")
    void isNumber(String bits, boolean expectedIsNumber) {
        Day16 day = new Day16();
        boolean isNumber = day.isNumber(new Packet(bits, 3));
        assertEquals(expectedIsNumber, isNumber);
    }

    @ParameterizedTest
    @MethodSource("getValueCaseProvider")
    void getValue(String bits, int from, long expectedValue) {
        Day16 day = new Day16();
        long value = day.getValue(new Packet(bits, from));
        assertEquals(expectedValue, value);
    }

    @ParameterizedTest
    @MethodSource("readPacketCaseProvider")
    void readPacket(String bits, int expectedVersion) {
        Day16 day = new Day16();
        int packetVersion = day.readPacket(new Packet(bits));
        assertEquals(expectedVersion, packetVersion);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String packet, long expectedVersionSum) {
        Day16 day = new Day16();
        long sum = day.partTwo(packet.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList()));
        assertEquals(expectedVersionSum, sum);
    }

}