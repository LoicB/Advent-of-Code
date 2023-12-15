package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;
import java.util.stream.IntStream;


public class Day15 implements Day<String, Number> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day15());
    }

    @Override
    public Number partOne(String sequence) {
        return Arrays
                .stream(sequence.split(","))
                .mapToInt(this::hashAlgorithm)
                .sum();
    }

    int hashAlgorithm(String string) {
        int hash = 0;
        for (int i = 0; i < string.length(); i++) {
            hash += string.charAt(i);
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }

    @Override
    public Number partTwo(String sequence) {
        return boxPower(buildBoxes(sequence));
    }

    int boxPower(List<LinkedHashMap<LensLabel, Integer>> boxes) {
        return IntStream
                .range(0, boxes.size())
                .map(index -> focusingLensesPower(boxes.get(index), index))
                .sum();
    }

    int focusingLensesPower(LinkedHashMap<LensLabel, Integer> box, int boxIndex) {
        Collection<Integer> values = box.values();
        int slotCpt = 1;
        int sum = 0;
        for (int value : values) {
            sum += (boxIndex + 1) * slotCpt++ * value;
        }
        return sum;
    }


    List<LinkedHashMap<LensLabel, Integer>> buildBoxes(String sequence) {
        List<LinkedHashMap<LensLabel, Integer>> boxes = IntStream
                .range(0, 256)
                .mapToObj(value -> new LinkedHashMap<LensLabel, Integer>())
                .toList();
        String[] instructions = sequence.split(",");
        for (String instruction : instructions) {
            if (isAddingOperation(instruction)) {
                Lens lens = createlens(instruction);
                boxes.get(lens.label().hashCode()).put(lens.label(), lens.focalLength());
            } else {
                LensLabel label = new LensLabel(instruction.substring(0, instruction.length() - 1));
                boxes.get(label.hashCode()).remove(label);
            }
        }
        return boxes;
    }

    private boolean isAddingOperation(String string) {
        return string.contains("=");
    }

    private Lens createlens(String string) {
        String[] arr = string.split("=");
        return new Lens(arr[0], Integer.parseInt(arr[1]));
    }

    record LensLabel(String label, HashCoder coder) {
        LensLabel(String label) {
            this(label, new HashCoder());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LensLabel lensLabel = (LensLabel) o;

            return Objects.equals(label, lensLabel.label);
        }

        @Override
        public int hashCode() {
            return label != null ? coder.hash(label) : 0;
        }
    }

    record HashCoder() {
        int hash(String string) {
            int hash = 0;
            for (int i = 0; i < string.length(); i++) {
                hash += string.charAt(i);
                hash *= 17;
                hash %= 256;
            }
            return hash;
        }
    }

    record Lens(LensLabel label, int focalLength) {
        Lens(String label, int focalLength) {
            this(new LensLabel(label), focalLength);
        }
    }

}
