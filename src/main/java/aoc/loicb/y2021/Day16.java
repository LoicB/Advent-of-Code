package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

enum TypeId {
    sum, product, minimum, maximum, literal, greater, less, equal
}

public class Day16 implements Day<List<Character>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<Character>> de = new DayExecutor<>(rawInput -> rawInput.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList()));
        de.execute(new Day16());
    }

    @Override
    public Integer partOne(List<Character> transmission) {
        String bits = convertToBit(transmission);
        return readPacket(new Packet(bits));
    }

    protected int readPacket(Packet packet) {
        int packetVersion = getVersion(packet);
        if (isNumber(packet)) {
            getValue(packet);
        } else {
            packetVersion += readOperator(packet, integer -> readPacket(packet)).stream().mapToInt(Number::intValue).sum();
        }
        return packetVersion;

    }

    private List<Number> readOperator(Packet packet, Function<Packet, Number> packetReader) {
        int lengthTypeId = packet.getCurrentBit();
        int length = Integer.parseInt(getSegment(packet, (lengthTypeId == '0') ? 15 : 11), 2);
        if (lengthTypeId == '0') return readOperatorWithLength(packet, length, packetReader);
        return readOperatorSubPackets(packet, length, packetReader);
    }

    private List<Number> readOperatorWithLength(Packet packet, int length, Function<Packet, Number> packetReader) {
        List<Number> literals = new ArrayList<>();
        int currentHead = packet.getHead();
        while (length > packet.getHead() - currentHead) {
            literals.add(packetReader.apply(packet));
        }
        return literals;
    }

    private List<Number> readOperatorSubPackets(Packet packet, int numberOfPackets, Function<Packet, Number> packetReader) {
        List<Number> literals = new ArrayList<>();
        for (int i = 0; i < numberOfPackets; i++) {
            literals.add(packetReader.apply(packet));
        }
        return literals;
    }

    protected int getVersion(Packet packet) {
        return readValue(packet);
    }

    protected boolean isNumber(Packet packet) {
        return getTypeId(packet) == TypeId.literal;
    }

    protected TypeId getTypeId(Packet packet) {
        return TypeId.values()[readValue(packet)];
    }

    protected int readValue(Packet packet) {
        int value = 0;
        if (packet.getCurrentBit() == '1') value += 4;
        if (packet.getCurrentBit() == '1') value += 2;
        if (packet.getCurrentBit() == '1') value += 1;
        return value;
    }

    protected long getValue(Packet packet) {
        boolean keepGoing = packet.getCurrentBit() == '1';
        StringBuilder sb = new StringBuilder();
        while (keepGoing) {
            appendSegment(packet, 4, sb);
            keepGoing = packet.getCurrentBit() == '1';
        }
        appendSegment(packet, 4, sb);
        return new BigInteger(sb.toString(), 2).longValue();
    }

    private void appendSegment(Packet packet, int size, StringBuilder stringBuilder) {
        for (int i = 0; i < size; i++) {
            stringBuilder.append(packet.getCurrentBit());
        }
    }

    private String getSegment(Packet packet, int size) {
        StringBuilder sb = new StringBuilder();
        appendSegment(packet, size, sb);
        return sb.toString();
    }

    protected String convertToBit(List<Character> packet) {
        return packet.stream().map(this::convertToBit).collect(Collectors.joining());
    }


    private String convertToBit(char c) {
        return switch (c) {
            default -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
        };
    }

    @Override
    public Long partTwo(List<Character> transmission) {
        String bits = convertToBit(transmission);
        return readPacketPartTwo(new Packet(bits));
    }


    protected long readPacketPartTwo(Packet packet) {
        int packetVersion = getVersion(packet);
        TypeId typeId = getTypeId(packet);
        if (typeId == TypeId.literal) {
            return getValue(packet);
        } else {
            List<Number> values = readOperator(packet, integer -> readPacketPartTwo(packet));
            switch (typeId) {
                case sum:
                    return values.stream().mapToLong(Number::longValue).sum();
                case product:
                    return values.stream().mapToLong(Number::longValue).reduce((left, right) -> left * right).orElseThrow();
                case minimum:
                    return values.stream().mapToLong(Number::longValue).min().orElseThrow();
                case maximum:
                    return values.stream().mapToLong(Number::longValue).max().orElseThrow();
                case greater:
                    return values.get(0).longValue() > values.get(1).longValue() ? 1 : 0;
                case less:
                    return values.get(0).longValue() < values.get(1).longValue() ? 1 : 0;
                case equal:
                    return values.get(0).longValue() == values.get(1).longValue() ? 1 : 0;
            }
        }
        return packetVersion;
    }
}

class Packet {
    private final String bits;
    private int head;

    Packet(String bits) {
        this(bits, 0);
    }

    Packet(String bits, int head) {
        this.bits = bits;
        this.head = head;
    }

    public int getHead() {
        return head;
    }

    public char getCurrentBit() {
        return bits.charAt(head++);
    }
}