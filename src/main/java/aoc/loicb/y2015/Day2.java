package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.Arrays;

public class Day2 implements Day<Box[], Integer> {
    public static void main(String[] args) {
        DayExecutor<Box[]> de = new DayExecutor<>(rawInput -> {
            String[] arr = rawInput.split("\\r?\\n");
            return Arrays.stream(arr).map(s -> {
                String[] dimensions = s.split("x");
                return new Box(Integer.parseInt(dimensions[0]),
                        Integer.parseInt(dimensions[1]),
                        Integer.parseInt(dimensions[2]));
            }).toArray(Box[]::new);
        });
        de.execute(new Day2());

    }

    @Override
    public Integer partOne(Box[] input) {
        return Arrays.stream(input)
                .map(this::calculateSurface)
                .reduce(0, Integer::sum);
    }

    private int calculateSurface(Box box) {
        int side1 = box.getLength() * box.getWidth();
        int side2 = box.getWidth() * box.getHeight();
        int side3 = box.getHeight() * box.getLength();
        return 2 * (side1 + side2 + side3) + Math.min(side1, Math.min(side2, side3));
    }

    @Override
    public Integer partTwo(Box[] input) {
        return Arrays.stream(input)
                .map(this::calculateRibbon)
                .reduce(0, Integer::sum);
    }

    private int calculateRibbon(Box box) {
        int[] sides = new int[]{box.getHeight(), box.getWidth(), box.getLength()};
        Arrays.sort(sides);
        return 2 * (sides[0] + sides[1]) + Arrays.stream(sides).reduce(1, (left, right) -> left * right);
    }
}

record Box(int length, int width, int height) {

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
