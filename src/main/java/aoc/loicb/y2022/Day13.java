package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Order {
    RIGHT, UNKNOWN, WRONG
}


interface PacketItem {

}

public class Day13 implements Day<List<PacketPair>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<PacketPair>> de = new DayExecutor<>(new Day13InputTransformer());
        de.execute(new Day13());
    }

    @Override
    public Integer partOne(List<PacketPair> input) {
        return IntStream.range(0, input.size()).filter(index -> isRight(input.get(index))).map(index -> index + 1).sum();
    }

    private boolean isRight(PacketPair packetPair) {
        return compare(packetPair) == Order.RIGHT;
    }

    protected Order compare(PacketPair packetPair) {
        return compare(packetPair.packet1(), packetPair.packet2());
    }

    private Order compare(PacketItem packet1, PacketItem packet2) {
        if (packet1 instanceof PacketList packetList && packet2 instanceof PacketList packetList2) {
            return compare(packetList, packetList2);
        } else if (packet1 instanceof PacketList packetList && packet2 instanceof PacketInt packetInt) {
            return compare(packetList, new PacketList(List.of(packetInt)));
        } else if (packet1 instanceof PacketInt packetInt && packet2 instanceof PacketList packetList) {
            return compare(new PacketList(List.of(packetInt)), packetList);
        } else if (packet1 instanceof PacketInt packetInt && packet2 instanceof PacketInt packetInt1) {
            return compare(packetInt, packetInt1);
        }
        return Order.UNKNOWN;
    }


    private Order compare(PacketList packet1, PacketList packet2) {
        int size = Math.min(packet1.packetItems().size(), packet2.packetItems().size());
        for (int i = 0; i < size; i++) {
            Order compare = compare(packet1.packetItems().get(i), packet2.packetItems().get(i));
            if (compare != Order.UNKNOWN) return compare;
        }
        return compare(packet1.packetItems().size(), packet2.packetItems().size());
    }

    private Order compare(PacketInt packet1, PacketInt packet2) {
        return compare(packet1.value(), packet2.value());
    }

    private Order compare(int int1, int int2) {
        if (int1 < int2) return Order.RIGHT;
        if (int1 > int2) return Order.WRONG;
        return Order.UNKNOWN;
    }

    @Override
    public Integer partTwo(List<PacketPair> input) {
        PacketPair additionalPair = new PacketPair(new PacketList(List.of(new PacketInt(2))), new PacketList(List.of(new PacketInt(6))));
        List<PacketItem> packetItems = new ArrayList<>();
        input.forEach(packetPair -> packetItems.addAll(List.of(packetPair.packet1(), packetPair.packet2())));
        packetItems.add(additionalPair.packet1());
        packetItems.add(additionalPair.packet2());
        packetItems.sort(this::compareToSort);
        return (packetItems.indexOf(additionalPair.packet1()) + 1) * (packetItems.indexOf(additionalPair.packet2()) + 1);
    }

    private int compareToSort(PacketItem packetItem1, PacketItem packetItem2) {
        if (compare(packetItem1, packetItem2) == Order.RIGHT) return -1;
        if (compare(packetItem1, packetItem2) == Order.WRONG) return 1;
        return 0;
    }
}

class Day13InputTransformer implements InputTransformer<List<PacketPair>> {
    @Override
    public List<PacketPair> transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        List<PacketPair> packetPairs = new ArrayList<>();
        for (int i = 0; i < inputLines.length; i += 3) {
            packetPairs.add(transformPacketPair(inputLines[i], inputLines[i + 1]));
        }
        return packetPairs;
    }

    private PacketPair transformPacketPair(String line1, String line2) {
        return new PacketPair(transformPacketItem(line1), transformPacketItem(line2));
    }

    private PacketItem transformPacketItem(String line) {
        if (isList(line)) {
            return new PacketList(split(line.substring(1, line.length() - 1)).stream().map(this::transformPacketItem)
                    .collect(Collectors.toList()));
        }
        return new PacketInt(Integer.parseInt(line));
    }

    private List<String> split(String content) {
        List<String> split = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int inBrackets = 0;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '[') {
                inBrackets++;
            } else if (content.charAt(i) == ']') {
                inBrackets--;
            }

            if (inBrackets == 0 && content.charAt(i) == ',') {
                split.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(content.charAt(i));
            }

        }
        if (!current.isEmpty()) split.add(current.toString());
        return split;
    }

    private boolean isList(String content) {
        return content.matches("\\[(.+(,.+)*)*]");
    }


}

record PacketPair(PacketItem packet1, PacketItem packet2) {

}

record PacketInt(int value) implements PacketItem {

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

record PacketList(List<PacketItem> packetItems) implements PacketItem {

    @Override
    public String toString() {
        return "[" + packetItems.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
    }

}