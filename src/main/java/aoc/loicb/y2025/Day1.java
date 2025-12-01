package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day1 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day1());
    }

    @Override
    public Integer partOne(List<String> rotations) {
        int position = 50;
        int password = 0;
        for (String rotation : rotations) {
            position = applyRotation(rotation, position);
            if (position == 0) {
                password++;
            }
        }
        return password;
    }

    Integer applyRotation(String rotation, int current) {
        return applyRotation(convertRotation(rotation), current);
    }

    Integer applyRotation(int rotation, int current) {
        return (current + rotation + 100) % 100;
    }

    Integer convertRotation(String rotation) {
        int number = Integer.parseInt(rotation.substring(1));
        return rotation.charAt(0) == 'L' ? number * -1 : number;
    }

    @Override
    public Integer partTwo(List<String> rotations) {
        int position = 50;
        int password = 0;
        for (String rotation : rotations) {
            int numberOfRotation = Math.abs(convertRotation(rotation));
            int stepRotation = rotation.charAt(0) == 'L' ? -1 : 1;
            int index = 0;
            while (index++ < numberOfRotation) {
                position = applyRotation(stepRotation, position);
                if (position == 0) {
                    password++;
                }
            }
        }
        return password;
    }
}
