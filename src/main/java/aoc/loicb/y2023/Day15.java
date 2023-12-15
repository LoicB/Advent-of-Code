package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        return buildBoxes(sequence)
                .stream()
                .mapToInt(this::boxPower)
                .sum();
    }

    int boxPower(Box box) {
        return focusingLensesPower(box.getContent(), box.index());
    }

    int focusingLensesPower(List<Lens> lenses, int boxIndex) {
        return IntStream
                .range(0, lenses.size())
                .map(slotNumber -> (boxIndex + 1) * (slotNumber + 1) * lenses.get(slotNumber).focalLength())
                .sum();
    }


    List<Box> buildBoxes(String sequence) {
        List<Box> boxes = IntStream
                .range(0, 256)
                .mapToObj(Box::new)
                .toList();
        String[] instructions = sequence.split(",");
        for (String instruction : instructions) {
            if (isAddingOperation(instruction)) {
                Lens lens = createlens(instruction);
                int hash = hashAlgorithm(lens.label());
                Box currentBox = boxes.get(hash);
                currentBox.add(lens);
            } else {
                String label = instruction.substring(0, instruction.length() - 1);
                int hash = hashAlgorithm(label);
                Box currentBox = boxes.get(hash);
                currentBox.remove(label);
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

    record Lens(String label, int focalLength) {
    }

    record Box(int index, List<Lens> lenses) {

        Box(int index) {
            this(index, new ArrayList<>());
        }

        List<Lens> getContent() {
            return lenses;
        }

        void add(Lens lens) {
            Optional<Lens> lensToRemove = getExistingLens(lens.label());
            if (lensToRemove.isPresent()) {
                int index = lenses.indexOf(lensToRemove.get());
                lenses.remove(index);
                lenses.add(index, lens);
            } else {
                lenses.add(lens);
            }

        }

        void remove(String lensLabel) {
            Optional<Lens> lensToRemove = getExistingLens(lensLabel);
            lensToRemove.ifPresent(lenses::remove);
        }

        private Optional<Lens> getExistingLens(String lensLabel) {
            return lenses.stream().filter(lens -> lens.label().equals(lensLabel)).findFirst();
        }
    }
}
